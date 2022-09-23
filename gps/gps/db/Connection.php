<?php

/**
 * Created by dominiccue.
 * User: dominiccue
 * Date: 1/24/2017
 * Time: 10:13 AM
 */
class Connection{
    function getConnection(){
        $host       = '127.0.0.1';
        $username   = 'root';
        $password   = '';
        $dbname     = 'gps';

        try{
            $conn    = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            return $conn;
        }catch (PDOException $e){
            echo "ERROR CONNECTIONF : " . $e->getMessage();
        }
    }
}