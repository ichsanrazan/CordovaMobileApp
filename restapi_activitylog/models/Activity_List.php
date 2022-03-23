<?php

class Activity_List
{
    //DB Properties
    private $conn;
    private $table = 'activity_list';

    //Activity List Properties
    public $activity_id;
    public $crq_date;
    public $crq_subject;
    public $pic_reporter;
    public $category;
    public $crq_no;
    public $crq_activity;
    public $crq_serviceimp;
    public $crq_pic;
    public $owner;

    //Constructor
    public function __construct($db)
    {
        $this->conn = $db;
    }

    //GET Activity
    public function read()
    {
        $query = 'SELECT * FROM ' . $this->table . ' ORDER BY crq_date DESC';
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        return $stmt;
    }

    //Check for duplicates based on crq_date, subject, and no
    public function read_check_duplicates()
    {
        $query = 'SELECT * FROM ' . $this->table . ' WHERE crq_date ="' . $this->crq_date . '" AND crq_subject ="' . $this->crq_subject . '" AND crq_no ="' . $this->crq_no . '" LIMIT 1';
        $stmt = $this->conn->prepare($query);

        $stmt->execute();
        $num = $stmt->rowCount();
        if ($num == 0 || $num == 'undefined') {
            return true;
        } else {
            return false;
        }
    }

    //POST Activity
    public function create()
    {
        $query = 'INSERT INTO ' . $this->table . ' SET 
            crq_date = :crq_date,
            crq_subject = :crq_subject,
            pic_reporter = :pic_reporter,
            category = :category,
            crq_no = :crq_no,
            crq_activity = :crq_activity,
            crq_serviceimp = :crq_serviceimp,
            crq_pic = :crq_pic,
            owner = :owner';

        // Prepare statement
        $stmt = $this->conn->prepare($query);

        // Clean data
        $this->crq_date = htmlspecialchars(strip_tags($this->crq_date));
        $this->crq_subject = htmlspecialchars(strip_tags($this->crq_subject));
        $this->pic_reporter = htmlspecialchars(strip_tags($this->pic_reporter));
        $this->category = htmlspecialchars(strip_tags($this->category));
        $this->crq_no = htmlspecialchars(strip_tags($this->crq_no));
        $this->crq_activity = htmlspecialchars(strip_tags($this->crq_activity));
        $this->crq_serviceimp = htmlspecialchars(strip_tags($this->crq_serviceimp));
        $this->crq_pic = htmlspecialchars(strip_tags($this->crq_pic));
        $this->owner = htmlspecialchars(strip_tags($this->owner));


        //Bind Data
        $stmt->bindParam(':crq_date', $this->crq_date);
        $stmt->bindParam(':crq_subject', $this->crq_subject);
        $stmt->bindParam(':pic_reporter', $this->pic_reporter);
        $stmt->bindParam(':category', $this->category);
        $stmt->bindParam(':crq_no', $this->crq_no);
        $stmt->bindParam(':crq_activity', $this->crq_activity);
        $stmt->bindParam(':crq_serviceimp', $this->crq_serviceimp);
        $stmt->bindParam(':crq_pic', $this->crq_pic);
        $stmt->bindParam(':owner', $this->owner);

        if ($stmt->execute()) {
            return true;
        }

        // Print error
        printf("Error: %s.\n", $stmt->error);

        return false;
    }

    //UPDATE Activity
    public function update()
    {
        $query = 'UPDATE ' . $this->table . ' SET 
            crq_date = :crq_date,
            crq_subject = :crq_subject,
            pic_reporter = :pic_reporter,
            category = :category,
            crq_no = :crq_no,
            crq_activity = :crq_activity,
            crq_serviceimp = :crq_serviceimp,
            crq_pic = :crq_pic,
            owner = :owner' . ' WHERE activity_id = :activity_id';

        // Prepare statement
        $stmt = $this->conn->prepare($query);

        // Clean data
        $this->crq_date = htmlspecialchars(strip_tags($this->crq_date));
        $this->crq_subject = htmlspecialchars(strip_tags($this->crq_subject));
        $this->pic_reporter = htmlspecialchars(strip_tags($this->pic_reporter));
        $this->category = htmlspecialchars(strip_tags($this->category));
        $this->crq_no = htmlspecialchars(strip_tags($this->crq_no));
        $this->crq_activity = htmlspecialchars(strip_tags($this->crq_activity));
        $this->crq_serviceimp = htmlspecialchars(strip_tags($this->crq_serviceimp));
        $this->crq_pic = htmlspecialchars(strip_tags($this->crq_pic));
        $this->owner = htmlspecialchars(strip_tags($this->owner));

        $this->activity_id = htmlspecialchars(strip_tags($this->activity_id));


        //Bind Data
        $stmt->bindParam(':crq_date', $this->crq_date);
        $stmt->bindParam(':crq_subject', $this->crq_subject);
        $stmt->bindParam(':pic_reporter', $this->pic_reporter);
        $stmt->bindParam(':category', $this->category);
        $stmt->bindParam(':crq_no', $this->crq_no);
        $stmt->bindParam(':crq_activity', $this->crq_activity);
        $stmt->bindParam(':crq_serviceimp', $this->crq_serviceimp);
        $stmt->bindParam(':crq_pic', $this->crq_pic);
        $stmt->bindParam(':owner', $this->owner);

        $stmt->bindParam(':activity_id', $this->activity_id);

        if ($stmt->execute()) {
            return true;
        }

        // Print error
        printf("Error: %s.\n", $stmt->error);

        return false;
    }

    //DELETE Activity
    public function delete()
    {
        $query = 'DELETE FROM ' . $this->table . ' WHERE activity_id = :activity_id';

        //Prepare statement
        $stmt = $this->conn->prepare($query);

        //Clean data
        $this->activity_id = htmlspecialchars(strip_tags($this->activity_id));

        //Bind data
        $stmt->bindParam(':activity_id', $this->activity_id);

        if ($stmt->execute()) {
            return true;
        }

        // Print error
        printf("Error: %s.\n", $stmt->error);

        return false;
    }
}
