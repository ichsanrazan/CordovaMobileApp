<?php

class Database
{
    //DB Config
    private $host = 'localhost';
    private $db_name = 'activity';
    private $username = 'root';
    private $password = '';
    private $conn;

    //Function used to make connection
    public function connect()
    {
        $this->conn = null;
        try {
            $this->conn = new PDO('mysql:host=' . $this->host . ';dbname=' . $this->db_name,  $this->username, $this->password);
            $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo 'Error connecting: ' . $e->getMessage();
        }
        return $this->conn;
    }
}
