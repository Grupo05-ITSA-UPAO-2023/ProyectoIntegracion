package com.example.proyectointegracion;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class PagoActivity extends AppCompatActivity {

    Button payment;
    String PublishableKey = "pk_test_51OFsOuAuV7PWfe9Dpe6mTuiEunFkvPii4ODU48f0tB2puygvuBcwU0A0OPZaMqNjAISuNzESJGdcr8ehpyWtYnzj00Pm9jO69N";
    String SecretKey = "sk_test_51OFsOuAuV7PWfe9D8juIlpmR5ufAbU7ev5SwaEAoYfFCM6Ro3SVbcATFhLybKJOaS9npEng9MCPPsGUrhZ4S8MrH000D63fmAQ";
    String CustomerId;
    String EphericalKey;
    String ClientSecret;
    PaymentSheet paymentSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        payment = findViewById(R.id.payment);
        PaymentConfiguration.init(this, PublishableKey);
        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        payment.setOnClickListener(v -> showBiometricPrompt());

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            CustomerId = object.getString("id");

                            Toast.makeText(PagoActivity.this, CustomerId, Toast.LENGTH_SHORT).show();

                            getEmphericalKey();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode,
                                                      CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(PagoActivity.this, "No cuentas con Huella Dactilar: ", Toast.LENGTH_SHORT).show();
                        paymentFlow();
                    }

                    @Override
                    public void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        // La autenticación fue exitosa, procede con el pago
                        paymentFlow();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(PagoActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación con Huella Dactilar")
                .setSubtitle("Toque el sensor de huellas dactilares")
                .setNegativeButtonText("Cancelar")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }


    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Learn with Arvind", new PaymentSheet.CustomerConfiguration(
                CustomerId,
                EphericalKey
        )));
    }


    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEmphericalKey() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            EphericalKey = object.getString("id");

                            Toast.makeText(PagoActivity.this, CustomerId, Toast.LENGTH_SHORT).show();

                            getClientSecret(CustomerId, EphericalKey);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                header.put("Stripe-Version", "2023-10-16");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getClientSecret(String customerId, String ephericalKey) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            ClientSecret = object.getString("client_secret");

                            Toast.makeText(PagoActivity.this, ClientSecret, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                params.put("amount", "100" + "00");
                params.put("currency", "USD");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void handleVolleyError(VolleyError error) {
        String errorMessage = error.getLocalizedMessage();
        if (errorMessage != null) {
            Toast.makeText(PagoActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PagoActivity.this, "Ocurrió un error en la solicitud", Toast.LENGTH_SHORT).show();
        }
        Log.e("PagoActivity", "Error en la solicitud a la API de Stripe", error);
    }
}
