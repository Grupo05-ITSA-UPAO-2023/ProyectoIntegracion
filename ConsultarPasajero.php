<?php
if ($_SERVER["REQUEST_METHOD"] == "GET") {
    $dni = isset($_GET['dni']) ? $_GET['dni'] : "";

    // Verificar si se proporcionó el DNI
    if (empty($dni)) {
        $response = array(
            'error' => 'Por favor, ingresa un número de DNI.'
        );
    } else {
        // Datos
        $token = 'apis-token-6382.vDw4orsZI8C8pjYP4zNEQzWwyziYIMqS';

        // Iniciar llamada a API
        $curl = curl_init();

        // Buscar DNI
        curl_setopt_array($curl, array(
            // para user api versión 1
            CURLOPT_URL => 'https://api.apis.net.pe/v1/dni?numero=' . $dni,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_SSL_VERIFYPEER => 0,
            CURLOPT_ENCODING => '',
            CURLOPT_MAXREDIRS => 2,
            CURLOPT_TIMEOUT => 0,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_CUSTOMREQUEST => 'GET',
            CURLOPT_HTTPHEADER => array(
                'Referer: https://apis.net.pe/consulta-dni-api',
                'Authorization: Bearer ' . $token
            ),
        ));

        $response = curl_exec($curl);

        curl_close($curl);

        // Datos listos para usar
        $persona = json_decode($response);

        // Formatear la respuesta en el formato deseado
        $formattedResponse = array(
            'numeroDocumento' => $dni,
            'nombre' => $persona->nombres,
            'apellidoPaterno' => $persona->apellidoPaterno,
            'apellidoMaterno' => $persona->apellidoMaterno
        );

        $response = $formattedResponse;
    }

    // Enviar la respuesta en formato JSON
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>

