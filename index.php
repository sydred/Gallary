<!DOCTYPE html>
<html lang='ru'>
    <head>
        <title>Gallary</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="styling.css">
    </head>
    <body>
    <header>
        <div class="tittle_head">
            <div class="tittle1"><h1>Галлерея</h1> </div>
            
            
             <div class="upload_photo">
                <h2>Загрузить</h2>
                <form action="uploadimg.php" method="POST" enctype="multipart/form-data">
                    <input type="text" name="filename" placeholder="имя файла...">
                    <input type="file" name="file">
                    <button type="submit" value="submit" name="submit">Загрузить</button>
                </form>
                </div>
        </div>
    </header>

    <main>
    
            <?php
            include_once "dbh.inc.php";

            $sql = 'SELECT * FROM gallery;';
            $stmt = mysqli_stmt_init($conn);
            if (!mysqli_stmt_prepare($stmt, $sql)) {
                echo "SQL STMT FAIL";
            } else {
                 mysqli_stmt_execute($stmt);
                $result = mysqli_stmt_get_result($stmt);
                while ($row = mysqli_fetch_assoc($result)) {
                echo '<div class="photo_block">
                <img src="/img/gallery/'.$row["namer"].'" alt ="нет картинки">
                    <form action="delete.php" method="POST">
                    <input type="hidden" name= "id" value = "'.$row['id'].'">
                        <button type="submit" value="submit" name="delete">удалить</button>
                    </form>
                </div>';
                     }
            }   
           
        ?>
        
        <div class="pop_up">
            <span>&times;</span>
            <img src="" alt="">
         </div>

        <script>
           document.querySelectorAll('main img').forEach(Img =>{
            Img.onclick = () => {
                document.querySelector('.pop_up').style.display = 'block';
                document.querySelector('.pop_up img').src = Img.getAttribute('src');
            }
        });

        document.querySelector('.pop_up span').onclick = () => {
            document.querySelector('.pop_up').style.display = 'none';
        }

        </script>


    </main>

    </body>


</html>