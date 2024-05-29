if ($conn->conect_error) {
    die("ошибка" .$conn->connect);
}


<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if(isset($_POST ['submit'])) {

    $newFileName = $_POST['filename'];
    if(empty($newFileName)){
        $newFileName = "gallary";

    } else {
        $newFileName = strtolower(str_replace(" ", "-", $newFileName));

    }
    $file = $_FILES["file"];


    $filename = $file['name'];
    $fileType = $file['type'];
    $fileTmpName = $file['tmp_name'];
    $fileError = $file['error'];
    $fileSize = $file['size'];

    $fileExt = explode('.', $filename);
    $fileActualExt = strtolower(end($fileExt));

    $allowed = array("jpg", "jpeg", "png");
    if(in_array($fileActualExt, $allowed)){
        if ($fileError === 0){
            if($fileSize < 20000000) {
                $imageFullName = $newFileName . "." . uniqid("", true) . "." . $fileActualExt;
                $fileDest = "C:/OSPanel/domains/localhost/img/gallery/" . $imageFullName;
                include_once "dbh.inc.php";
                $sql = "SELECT * FROM gallery;";
                $stmt - mysqli_stmt_init($conn);
                if(!mysqli_stmt_prepare($stmt, $sql)) {
                    echo "SQL ошибка";
                } else {
                    mysqli_stmt_execute($stmt);
                    $result = mysqli_stmt_get_result($stmt);
                    $rowCownt = mysqli_num_rows($result);
                    $imgName = $rowCownt + 1;
                    
                    $sql = "INSERT INT gallery (namer , name) Values (?, ?);";
                    if(!mysqli_stmt_prepare($stmt, $sql)) {
                        echo "SQL ошибка";
                        
                } else {
                    $imgName = $newFileName;
                    mysqli_stmt_bind_param($stmt, "s" , $imageFullName, $imgName );
                    mysqli_stmt_execute($stmt);

                    move_uploaded_file($fileTmpName, $fileDest);

                    header("Location: http://localhost/index.php?upload=success");
                    exit();
                }
            }      
            } else {
                echo "слишком большой размер файла";
                exit();

            }
        } else {
            echo " ошибка";
            exit();
        }
    } else {
        echo "не поддерживаемый формат файла";
        exit();
    }

}

<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if (isset($_POST['submit'])) {
    $newFileName = $_POST['filename'];
    if (empty($newFileName)) {
        $newFileName = "gallery";
    } else {
        $newFileName = strtolower(str_replace(" ", "-", $newFileName));
    }
    $file = $_FILES["file"];
    $filename = $file['name'];
    $fileTmpName = $file['tmp_name'];
    $fileError = $file['error'];
    $fileSize = $file['size'];
    $fileExt = explode('.', $filename);
    $fileActualExt = strtolower(end($fileExt));
    $allowed = array("jpg", "jpeg", "png");
    if (in_array($fileActualExt, $allowed)) {
        if ($fileError === 0) {
            if ($fileSize < 2000000) {
                $imageFullName = $newFileName . "." . uniqid("", true) . "." . $fileActualExt;
                $fileDest = "C:/OSPanel/domains/localhost/img/gallery/" . $imageFullName;

                include_once "dbh.inc.php";
                $sql = "INSERT INTO gallery (namer, name) VALUES (?, ?);";
                $stmt = mysqli_stmt_init($conn);
                if (!mysqli_stmt_prepare($stmt, $sql)) {
                    echo "SQL ошибка";
                    exit();
                } else {
                    mysqli_stmt_bind_param($stmt, "ss", $imageFullName, $newFileName);
                    mysqli_stmt_execute($stmt);
                    move_uploaded_file($fileTmpName, $fileDest);
                    header("Location: index.php?upload-success");
                    exit();
                }
            } else {
                echo "Слишком большой размер файла";
                exit();
            }
        } else {
            echo "Ошибка загрузки файла";
            exit();
        }
    } else {
        echo "Не поддерживаемый формат файла";
        exit();
    }
} else {
    header("Location: index.php");
    exit();
}
?>