<?php

class Standby_Schedule
{
    //DB Properties
    private $conn;
    private $schedule_table = 'schedule';
    private $employee_table = 'employee';

    public $id;
    public $date;
    public $pic;
    public $division;

    public function __construct($db)
    {
        $this->conn = $db;
    }

    public function insert_to_db($name, $division, $date_counter, $curr_month, $curr_year)
    {
        $date_string = $curr_year . "-" . $curr_month . "-" . $date_counter;

        $query = 'INSERT INTO schedule SET date = :date, pic = :pic, division = ' . '"' . $division . '"';
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':date', $date_string);
        $stmt->bindParam(':pic', $name);
        $stmt->execute();
    }


    // function checkScheduleDatabase($selected_date, $employee_division, $db)
    // {
    //     $flag = true;
    //     $stmt = $db->prepare('SELECT * FROM schedule WHERE division = "' . $employee_division . '"');
    //     $stmt->execute();

    //     while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
    //         $date = $row['date'];
    //         if ($date == $selected_date->format('Y-m-d')) {
    //             $flag = false;
    //         }
    //     }

    //     return $flag;
    // }

    public function generate_schedule_core()
    {
        $employee_query = 'SELECT * FROM ' . $this->employee_table . ' WHERE division = "Core"';
        $employee_stmt = $this->conn->prepare($employee_query);
        $employee_stmt->execute();

        $employee_array = array();

        while ($row = $employee_stmt->fetch(PDO::FETCH_ASSOC)) {
            $name = $row['name'];
            array_push($employee_array, $name);
        }

        $employee_count = $employee_stmt->rowCount();
        $curr_day_count = date('t');
        $standby_count = floor($curr_day_count / $employee_count); //Jumlah hari standby per 1 orang
        $remainder_days = $curr_day_count - ($employee_count * $standby_count); //Jumlah employee yang lebih 1 hari

        $first_date = new DateTime('first day of this month');
        $date_begin = $first_date->format('j');

        $date_counter = (int) $date_begin;
        $curr_month = $first_date->format('m');
        $curr_year = $first_date->format('Y');

        for ($i = 0; $i < $standby_count; $i++) {
            foreach ($employee_array as $name) {
                $this->insert_to_db($name, "Core", $date_counter, $curr_month, $curr_year);
                $date_counter++;
            }
        }

        if ($remainder_days > 0) {
            for ($i = 0; $i < $remainder_days; $i++) {
                $random_index = rand(0, count($employee_array) - 1);
                $this->insert_to_db((string) $employee_array[$random_index], "Core", $date_counter, $curr_month, $curr_year);
                $date_counter++;
                array_splice($employee_array, $random_index, 1);
            }
        }

        return $standby_count;
    }


    public function generate_schedule_datacomm()
    {
        $employee_query = 'SELECT * FROM ' . $this->employee_table . ' WHERE division = "Datacomm"';
        $employee_stmt = $this->conn->prepare($employee_query);
        $employee_stmt->execute();

        $employee_array = array();

        while ($row = $employee_stmt->fetch(PDO::FETCH_ASSOC)) {
            $name = $row['name'];
            array_push($employee_array, $name);
        }

        $employee_count = $employee_stmt->rowCount();
        $curr_day_count = date('t');
        $standby_count = floor($curr_day_count / $employee_count); //Jumlah hari standby per 1 orang
        $remainder_days = $curr_day_count - ($employee_count * $standby_count); //Jumlah employee yang lebih 1 hari

        $first_date = new DateTime('first day of this month');
        $date_begin = $first_date->format('j');

        $date_counter = (int) $date_begin;
        $curr_month = $first_date->format('m');
        $curr_year = $first_date->format('Y');

        for ($i = 0; $i < $standby_count; $i++) {
            foreach ($employee_array as $name) {
                $this->insert_to_db($name, "Datacomm", $date_counter, $curr_month, $curr_year);
                $date_counter++;
            }
        }

        if ($remainder_days > 0) {
            for ($i = 0; $i < $remainder_days; $i++) {
                $random_index = rand(0, count($employee_array) - 1);
                $this->insert_to_db((string) $employee_array[$random_index], "Datacomm", $date_counter, $curr_month, $curr_year);
                $date_counter++;
                array_splice($employee_array, $random_index, 1);
            }
        }

        return $standby_count;
    }

    //GET Schedule
    public function read()
    {
        $query = 'SELECT * FROM ' . $this->schedule_table;
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        return $stmt;
    }

    public function generate_schedule()
    {
        $this->generate_schedule_core();
        $this->generate_schedule_datacomm();
    }
}






// function checkScheduleDatabase($selected_date, $employee_division, $db)
// {
//     $flag = true;
//     $stmt = $db->prepare('SELECT * FROM schedule WHERE division = "' . $employee_division . '"');
//     $stmt->execute();

//     while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
//         $date = $row['date'];
//         if ($date == $selected_date->format('Y-m-d')) {
//             $flag = false;
//         }
//     }

//     return $flag;
// }

// function checkSelectedDate($date_array, $selected_date)
// {
//     $flag = true;

//     $temp_date_minus = clone $selected_date;
//     $temp_date_minus = $temp_date_minus->sub(new DateInterval('P1D'))->format("Y-m-d");

//     $temp_date_plus = clone $selected_date;
//     $temp_date_plus = $temp_date_plus->add(new DateInterval('P1D'))->format("Y-m-d");

//     foreach ($date_array as $i) {

//         if (
//             $i == $selected_date->format('Y-m-d') ||
//             $i == $temp_date_minus ||
//             $i == $temp_date_plus
//         ) {
//             $flag = false;
//         }
//     }
//     return $flag;
// }

// function generateWeekendDates($employee_division, $conn)
// {
//     $weekend_array = array();

//     $dateBegin = new DateTime('first day of this month');
//     $dateEnd = new DateTime('last day of this month');

//     $loop_control_weekend = true;
//     while ($loop_control_weekend) {
//         $randomTimestamp = mt_rand($dateBegin->getTimestamp(), $dateEnd->getTimestamp());
//         $randomDate = new DateTime();
//         $randomDate->setTimestamp($randomTimestamp);


//         if ($randomDate->format('N') >= 6) {
//             if (
//                 checkSelectedDate($weekend_array, $randomDate) == true &&
//                 checkScheduleDatabase($randomDate, $employee_division, $conn)
//             ) {
//                 array_push($weekend_array, $randomDate->format('Y-m-d'));
//             }
//         }
//         if (count($weekend_array) == 2) {
//             $loop_control_weekend = false;
//         }
//     }
//     return $weekend_array;
// }

// function generateWeekdayDates($employee_division, $conn)
// {
//     $weekday_array = array();

//     $dateBegin = new DateTime('first day of this month');
//     $dateEnd = new DateTime('last day of this month');

//     $loop_control_weekend = true;
//     while ($loop_control_weekend) {
//         $randomTimestamp = mt_rand($dateBegin->getTimestamp(), $dateEnd->getTimestamp());
//         $randomDate = new DateTime();
//         $randomDate->setTimestamp($randomTimestamp);

//         if ($randomDate->format('N') < 6) {
//             if (
//                 checkSelectedDate($weekday_array, $randomDate) == true &&
//                 checkScheduleDatabase($randomDate, $employee_division, $conn)
//             ) {
//                 array_push($weekday_array, $randomDate->format('Y-m-d'));
//             }
//         }
//         if (count($weekday_array) == 5) {
//             $loop_control_weekend = false;
//         }
//     }
//     return $weekday_array;
// }

// while ($row = $employee_stmt->fetch(PDO::FETCH_ASSOC)) {
//     $name = $row['name'];

// foreach ($array_schedule as $i) {
//     $query = 'INSERT INTO schedule SET date = :date, pic = :pic, division = "Core"';
//     array_push($date_array, (int) substr($i, -2));
//     array_push($date_array_unaltered, $i);
//     $stmt = $this->conn->prepare($query);
//     $stmt->bindParam(':date', $i);
//     $stmt->bindParam(':pic', $name);
//     $stmt->execute();
// }
//}

// $firstDate = new DateTime('first day of this month');
// $lastDate = new DateTime('last day of this month');
// $dateBegin = $firstDate->format('j');
// $dateEnd = $lastDate->format('j');
// $daysOfMonth = range((int) $dateBegin, (int) $dateEnd);

// $remainingDaysMerge = array_merge($daysOfMonth, $date_array);
// $remainingDaysUnique = array_unique($remainingDaysMerge);

// if ($remainder_days > 0) {
//     $firstDate = new DateTime('first day of this month');
//     $lastDate = new DateTime('last day of this month');

//     $dateBegin = $firstDate->format('d');
//     $dateEnd = $lastDate->format('d');
//     $daysOfMonth = range((int) $dateBegin, (int) $dateEnd);
//     $remainingDays = array_unique(array_merge($daysOfMonth, $date_array), SORT_REGULAR);

//     print_r($daysOfMonth);

//     for ($i = 0; $i < $remainder_days; $i++) {
//         $selected_employee = $employee_array[rand(0, count($employee_array) - 1)];

//         foreach ($date_array_unaltered as $date) {
//             if ((int) substr($date, -2) == $remainingDays[$i]) {
//                 $query = 'INSERT INTO schedule SET date = :date, pic = :pic, division = "Core"';
//                 $stmt = $this->conn->prepare($query);
//                 $stmt->bindParam(':date', $date);
//                 $stmt->bindParam(':pic', $selected_employee);
//                 $stmt->execute();
//             }
//         }
//     }
// }

// print_r(end($daysOfMonth));

// for ($i = 0; $i < count($daysOfMonth); $i++) {
//     print_r($daysOfMonth[$i]);
// }
