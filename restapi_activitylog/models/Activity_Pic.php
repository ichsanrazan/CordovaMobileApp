<?php

class Activity_Pic
{
    //DB Properties
    private $conn;
    private $table = 'activity_pic';

    //Activity List Properties
    public $id;
    public $company;
    public $full_name;
    public $phone_number;


    //Constructor
    public function __construct($db)
    {
        $this->conn = $db;
    }

    //GET Activity List
    public function read()
    {
        $query = 'SELECT * FROM ' . $this->table;
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        return $stmt;
    }

    //Check duplicates based on company, full name, and phone number
    public function read_check_duplicates()
    {
        $query = 'SELECT * FROM ' . $this->table . ' WHERE company ="' . $this->company . '" AND full_name ="' . $this->full_name . '" AND phone_number ="' . $this->phone_number . '" LIMIT 1';
        $stmt = $this->conn->prepare($query);

        $stmt->execute();
        $num = $stmt->rowCount();
        if ($num == 0 || $num == 'undefined') {
            return true;
        } else {
            return false;
        }
    }

    //POST Activity Pic
    public function create()
    {
        $query = 'INSERT INTO ' . $this->table . ' SET 
            company = :company,
            full_name = :full_name,
            phone_number = :phone_number';

        // Prepare statement
        $stmt = $this->conn->prepare($query);

        // Clean data
        $this->company = htmlspecialchars(strip_tags($this->company));
        $this->full_name = htmlspecialchars(strip_tags($this->full_name));
        $this->phone_number = htmlspecialchars(strip_tags($this->phone_number));

        //Bind Data
        $stmt->bindParam(':company', $this->company);
        $stmt->bindParam(':full_name', $this->full_name);
        $stmt->bindParam(':phone_number', $this->phone_number);

        if ($stmt->execute()) {
            return true;
        }

        // Print error
        printf("Error: %s.\n", $stmt->error);

        return false;
    }
}
