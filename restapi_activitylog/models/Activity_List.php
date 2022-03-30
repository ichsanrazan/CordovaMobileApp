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

    public function search($name)
    {
        $query = 'SELECT * FROM `activity_list` WHERE pic_reporter LIKE :name OR crq_activity LIKE :name OR crq_subject LIKE :name OR category LIKE :name OR crq_no LIKE :name';

        //Prepare statement
        $stmt = $this->conn->prepare($query);

        $stmt->execute(["name" => "%" . $name . "%"]);

        return $stmt;
    }

    public function filter($start_date, $end_date, $subject, $category)
    {
        $query = 'SELECT * FROM `activity_list` WHERE ';
        $and_flag = false;

        if (isset($start_date) && isset($end_date)) {

            if ($start_date !== "" && $end_date !== "") {
                $query .= 'crq_date BETWEEN ' . '"' . $start_date . '"' . ' AND ' . '"' . $end_date . '"';
                $and_flag = true;
            } else if ($start_date !== "" && $end_date == "") {
                $query .= 'crq_date BETWEEN ' . '"' . $start_date . '"' . ' AND ' . '"' . $start_date . '"';
                $and_flag = true;
            } else  if ($end_date !== "" && $start_date == "") {
                $query .= 'crq_date BETWEEN ' . '"' . $end_date . '"' . ' AND ' . '"' . $end_date . '"';
                $and_flag = true;
            }
        }

        if (isset($subject)) {
            if ($subject !== "") {
                $subject_array = explode(",", $subject);
                $subject_condition = "";

                foreach ($subject_array as $subject) {
                    $subject_condition .= 'crq_subject = ' . '"' . $subject . '"' . ' OR ';
                }
                $subject_condition = substr($subject_condition, 0, -4);

                if ($and_flag) {
                    $subject_condition = ' AND (' . $subject_condition . ')';
                } else {
                    $subject_condition = ' (' . $subject_condition . ')';
                }
                $and_flag = true;

                $query .= $subject_condition;
            }
        }

        if (isset($category)) {
            if ($category !== "") {
                $category_array = explode(",", $category);
                $category_condition = "";

                foreach ($category_array as $category) {
                    $category_condition .= 'category = ' . '"' . $category . '"' . ' OR ';
                }
                $category_condition = substr($category_condition, 0, -4);

                if ($and_flag) {
                    $category_condition = ' AND (' . $category_condition . ')';
                } else {
                    $category_condition = ' (' . $category_condition . ')';
                }

                $query .= $category_condition;
            }
        }

        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        return $stmt;
    }
}
