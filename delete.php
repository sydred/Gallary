<?php
if (isset($_POST['delete'])) {
    include_once "dbh.inc.php";
    $id = $_POST['id'];

    $sqlGet = "SELECT namer FROM gallery WHERE id = ?";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sqlGet)) {
        echo "SQL ошибка";
    } else {
        mysqli_stmt_bind_param($stmt, 'i', $id);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);
        $row = mysqli_fetch_assoc($result);
        if ($row) {
            $filename = $row['namer'];
            $filepath = "img/gallery/" . $filename;

            $sqlDel = "DELETE FROM gallery WHERE id = ?";
            if (mysqli_stmt_prepare($stmt, $sqlDel)) {
                mysqli_stmt_bind_param($stmt, 'i', $id);
                mysqli_stmt_execute($stmt);

                if (file_exists($filepath)) {
                    if (unlink($filepath)) {
                        echo "ФАЙЛ удален";
                    } else {
                        echo "Ошибка при удалении файла";
                    }
                }
            } else {
                echo "Ошибка при подготовке запроса на удаление";
            }
        } else {
            echo "Файл не найден в базе данных";
        }
    }
    header("Location: http://localhost/index.php?upload-success");
    exit();
}
?>