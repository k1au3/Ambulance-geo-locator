<?php
/**
 * Created by dominiccue.
 * User: dominiccue
 * Date: 1/24/2017
 * Time: 10:13 AM
 */
require_once("../db/Connection.php");
class InsertTask{
    function startInsertTask(){
        $connection = new Connection();
        $conn = $connection->getConnection();


        // array for json response
        // $response = array();

       $email=isset($_POST['email']) ? $_POST['email'] : '';
       $name= isset($_POST['name']) ? $_POST['name'] : '';
        $idno= isset($_POST['idno']) ? $_POST['idno'] : '';
        $phone= isset($_POST['phone']) ? $_POST['phone'] : '';
        $ambulance= isset($_POST['ambulance']) ? $_POST['ambulance'] : '';
        $loca= isset($_POST['loca']) ? $_POST['loca'] : '';
        $patient= isset($_POST['patient']) ? $_POST['patient'] : '';
        $location= isset($_POST['location']) ? $_POST['location'] : '';
        $tdate=date('Y-m-d H:i:s');


 // $sqlInsert = "INSERT INTO tasks (email,tdate,names,idno,phone,ambulance,physical_location,patients,location) VALUES ('$email','$tdate', '$name','$idno','$phone','$ambulance','$loca','$patient','$location')";
 //                $conn->exec($sqlInsert);

//         $data = file_get_contents("http://maps.google.com/maps/api/geocode/json?latlng=$location&sensor=false");
// $data = json_decode($data);
// $add_array  = $data->results;
// $add_array = $add_array[0];
// $add_array = $add_array->address_components;
// $country = "Not found";
// $state = "Not found"; 
// $city = "Not found";
// foreach ($add_array as $key) {
//   if($key->types[0] == 'route')
//   {
//     $city = $key->long_name;
//   }
//   if($key->types[0] == 'administrative_area_level_1')
//   {
//     $state = $key->long_name;
//   }
//   if($key->types[0] == 'country')
//   {
//     $country = $key->long_name;
//   }
// }
//         $locname=$country.",".$state.",".$city;
        try{
            if(isset($email) && isset($tdate) && isset($phone) && isset($idno) && isset($location)){
                $sqlInsert = "INSERT INTO tasks (email,tdate,idno,name,phone,ambulance,loca,patient,location) VALUES ('$email','$tdate','$idno','$name','$phone','$ambulance','$loca','$patient','$location')";
                $conn->exec($sqlInsert);
            }
        }catch (PDOException $e){
            echo "Error while inserting ".$e->getMessage();
        }

        //cek is the row was inserted or not
        if($sqlInsert){
            //success inserted
            $response["success"] = 1;
            $response["message"] = "Request submitted successfully";

            echo json_encode($response);

        }else{
            //failed inserted
            $response["success"] = 0;
            $response["message"] = "Failed";

            echo json_encode($response);
        }
    }
}

$insert = new InsertTask();
$insert->startInsertTask();;