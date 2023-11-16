<?php
if ($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET["origen"]) && isset($_GET["destino"])) {
    $origen = $_GET["origen"];
    $destino = $_GET["destino"];

    // Conectar a la base de datos
    $mysqli = new mysqli(
        "bvkg1zrzaljw9vfsxedl-mysql.services.clever-cloud.com",
        "unyrva5hyxlkzd9v",
        "mmKZ8sKOORgUGIqc5trc",
        "bvkg1zrzaljw9vfsxedl"
    );

    if ($mysqli->connect_error) {
        die("Conexión fallida: " . $mysqli->connect_error);
    }

    // Realizar la consulta con los valores ingresados de origen y destino
    $query = "SELECT 
                p.Id_Programacion AS Id,
                p.Hora_Salida AS Salida,
                b.Tipo_Bus AS Servicio,
                r.Origen AS Origen,
                r.Destino AS Destino,
                t.Embarque AS Embarque,
                t.Desembarque AS Desembarque,
                p.Precio AS Precio
              FROM
                Ruta r
              INNER JOIN 
                Programacion p ON r.Id_Ruta = p.Ruta_Id_Ruta
              INNER JOIN 
                Bus b ON p.Bus_Id_Bus = b.Id_Bus
              INNER JOIN 
                Terminales t ON p.Terminales_Id_Terminales = t.Id_Terminales
              WHERE r.Origen = '$origen' AND r.Destino = '$destino'";

    $result = $mysqli->query($query);

    if ($result) {
        $programacionArray = array();

        while ($row = $result->fetch_assoc()) {
            $programacionArray[] = $row;
        }

        // Liberar la memoria del resultado
        $result->free();

        // Cerrar la conexión a la base de datos
        $mysqli->close();

        if (!empty($programacionArray)) {
            header('Content-Type: application/json');
            echo json_encode($programacionArray);
        } else {
            echo json_encode(array("message" => "No se encontraron resultados para la ruta de origen '$origen' a destino '$destino'."));
        }
    } else {
        echo json_encode(array("message" => "No se encontraron resultados para la ruta de origen '$origen' a destino '$destino'."));
    }
} else {
    echo json_encode(array("message" => "Solicitud no válida. Asegúrate de proporcionar los parámetros 'origen' y 'destino' mediante una solicitud GET."));
}
?>