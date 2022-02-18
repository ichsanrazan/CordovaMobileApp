<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/*
|--------------------------------------------------------------------------
| Display Debug backtrace
|--------------------------------------------------------------------------
|
| If set to TRUE, a backtrace will be displayed along with php errors. If
| error_reporting is disabled, the backtrace will not display, regardless
| of this setting
|
*/
defined('SHOW_DEBUG_BACKTRACE') OR define('SHOW_DEBUG_BACKTRACE', TRUE);

/*
|--------------------------------------------------------------------------
| File and Directory Modes
|--------------------------------------------------------------------------
|
| These prefs are used when checking and setting modes when working
| with the file system.  The defaults are fine on servers with proper
| security, but you may wish (or even need) to change the values in
| certain environments (Apache running a separate process for each
| user, PHP under CGI with Apache suEXEC, etc.).  Octal values should
| always be used to set the mode correctly.
|
*/
defined('FILE_READ_MODE')  OR define('FILE_READ_MODE', 0644);
defined('FILE_WRITE_MODE') OR define('FILE_WRITE_MODE', 0666);
defined('DIR_READ_MODE')   OR define('DIR_READ_MODE', 0755);
defined('DIR_WRITE_MODE')  OR define('DIR_WRITE_MODE', 0755);

/*
|--------------------------------------------------------------------------
| File Stream Modes
|--------------------------------------------------------------------------
|
| These modes are used when working with fopen()/popen()
|
*/
defined('FOPEN_READ')                           OR define('FOPEN_READ', 'rb');
defined('FOPEN_READ_WRITE')                     OR define('FOPEN_READ_WRITE', 'r+b');
defined('FOPEN_WRITE_CREATE_DESTRUCTIVE')       OR define('FOPEN_WRITE_CREATE_DESTRUCTIVE', 'wb'); // truncates existing file data, use with care
defined('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE')  OR define('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE', 'w+b'); // truncates existing file data, use with care
defined('FOPEN_WRITE_CREATE')                   OR define('FOPEN_WRITE_CREATE', 'ab');
defined('FOPEN_READ_WRITE_CREATE')              OR define('FOPEN_READ_WRITE_CREATE', 'a+b');
defined('FOPEN_WRITE_CREATE_STRICT')            OR define('FOPEN_WRITE_CREATE_STRICT', 'xb');
defined('FOPEN_READ_WRITE_CREATE_STRICT')       OR define('FOPEN_READ_WRITE_CREATE_STRICT', 'x+b');

/*
|--------------------------------------------------------------------------
| Exit Status Codes
|--------------------------------------------------------------------------
|
| Used to indicate the conditions under which the script is exit()ing.
| While there is no universal standard for error codes, there are some
| broad conventions.  Three such conventions are mentioned below, for
| those who wish to make use of them.  The CodeIgniter defaults were
| chosen for the least overlap with these conventions, while still
| leaving room for others to be defined in future versions and user
| applications.
|
| The three main conventions used for determining exit status codes
| are as follows:
|
|    Standard C/C++ Library (stdlibc):
|       http://www.gnu.org/software/libc/manual/html_node/Exit-Status.html
|       (This link also contains other GNU-specific conventions)
|    BSD sysexits.h:
|       http://www.gsp.com/cgi-bin/man.cgi?section=3&topic=sysexits
|    Bash scripting:
|       http://tldp.org/LDP/abs/html/exitcodes.html
|
*/
defined('EXIT_SUCCESS')        OR define('EXIT_SUCCESS', 0); // no errors
defined('EXIT_ERROR')          OR define('EXIT_ERROR', 1); // generic error
defined('EXIT_CONFIG')         OR define('EXIT_CONFIG', 3); // configuration error
defined('EXIT_UNKNOWN_FILE')   OR define('EXIT_UNKNOWN_FILE', 4); // file not found
defined('EXIT_UNKNOWN_CLASS')  OR define('EXIT_UNKNOWN_CLASS', 5); // unknown class
defined('EXIT_UNKNOWN_METHOD') OR define('EXIT_UNKNOWN_METHOD', 6); // unknown class member
defined('EXIT_USER_INPUT')     OR define('EXIT_USER_INPUT', 7); // invalid user input
defined('EXIT_DATABASE')       OR define('EXIT_DATABASE', 8); // database error
defined('EXIT__AUTO_MIN')      OR define('EXIT__AUTO_MIN', 9); // lowest automatically-assigned error code
defined('EXIT__AUTO_MAX')      OR define('EXIT__AUTO_MAX', 125); // highest automatically-assigned error code


// ==================== DASHBOARD ====================
define("MAX_9_KPI", serialize(array(0, 0, 0, 0, 0, 0, 0, 0, 0)));
define("QUALITY_MSS", serialize(array("Complete Call Ratio (CCR)", "Success Call Ratio (SCR)", "Authentication SR (Auth SR)", "Paging SR", "Location Update SR (LOC SR)", "Handover SR (HO SR)", "CS Fallback Ratio (CSFBR)", "", "")));
define("QUALITY_DRA", serialize(array("Gx Success Ratio (Gx SR)", "Gy Success Ratio (Gy SR)", "S6a-AIR SR (S6a-AIR)", "S6a-ULR SR (S6a-ULR)", "", "", "", "", "")));
define("QUALITY_HLR", serialize(array("GPRS LOCUP SR", "Location Update SR", "SRI - SMS Success Ratio", "SRI - Call Success Ratio", "Update Location Req/ANS SR", "Authentication Req/ANS SR", "", "", "")));
define("QUALITY_CGR", serialize(array("CGR SCR", "", "", "", "", "", "", "", "")));
define("QUALITY_SGSN", serialize(array("PDP SR (2G/3G)", "PDP CR (2G/3G)", "PDN Connect SR (4G)", "Combine Attach SR (4G)", "Default Bearer SR (4G)", "INTER TAU SR (4G)", "INTRA TAU SR (4G)", "Service Request SR", "")));
define("QUALITY_GGSN", serialize(array("Gx Success Ratio (Gx SR)", "Gy Success Ratio (Gy SR)", "", "", "", "", "", "", "")));

define("CAPACITY_MSS", serialize(array("SCC - License Utility", "IuCSoIP - License Utility", "NBIP - License Utility", "AoIP - License Utility", "PCMB - License Utility", "CSFB - License Utility", "BHCA - License Utility", "CPU Load - License Utility", "VLR - License Utility")));
define("CAPACITY_DRA", serialize(array("License Utilization", "CPU Usage Utilization", "Memory Utilization", "Disk Usage (root) Utilization", "Disk Usage (data) Utilization", "", "", "", "")));
define("CAPACITY_HLR", serialize(array("HSS-BE CPU Load", "HSS-FE CPU Load", "HSS-BE Memory Usage", "HSS-FE Memory Usage", "", "", "", "", "")));
define("CAPACITY_CGR", serialize(array("CGR Occupancy", "", "", "", "", "", "", "", "")));
define("CAPACITY_SGSN", serialize(array("PDP - Utilization (2G/3G)", "SAU - Utilization (2G/3G)", "Throughput - Utilization", "Attach - Utilization (4G)", "PDN - Utilization (4G)", "CPU Load - Utilization", "", "", "")));
define("CAPACITY_GGSN", serialize(array("PDP - Utilization", "Throughput - Utilization", "Avail. IP Pool - Utilization", "CP CPU - Utilization", "UP CPU - Utilization", "CP Memory - Utilization", "UP Memory - Utilization", "", "")));

define("LIST_SGSN_JABO", serialize(array("SGBRN7", "SGBRN8", "SGBSD1", "SGBSD2", "SGBSD3", "SGTBS6", "SGTBS7", "SGTBS8", "SGTBS9")));
define("LIST_GGSN_JABO", serialize(array("GGBRN4", "GGBRN5", "GGBRN6", "GGBRN7", "GGBRN8", "GGBRN9", "GGBRN10", "GGTBS5", "GGTBS6", "GGTBS7", "GGTBS8", "GGTBS9", "GGTBS10", "GGTBS11")));
define("LIST_DRA_JABO_1", serialize(array("DEA_JKT1a", "DEA_JKT1b", "DRA_JKT1a", "DRA_JKT1b", "DRA_JKT2a", "DRA_JKT2b")));
define("LIST_DRA_JABO_2", serialize(array("DEA_JKT1a", "DEA_JKT1b", "DRA_JKT1a", "DRA_JKT1b", "DRA_JKT2a", "DRA_JKT2b", "OAM_JKT1a", "OAM_JKT1b", "OAM_JKT2a", "OAM_JKT2b")));

define("LIST_DUMMY", serialize(array("NE_JABO_1", "NE_JABO_2", "NE_JABO_3", "NE_JABO_4", "NE_JABO_5", "NE_JABO_6", "NE_JABO_7", "NE_JABO_8", "NE_JABO_9", "NE_JABO_10", "NE_JABO_11", "NE_JABO_12", "NE_JABO_13", "NE_JABO_14")));


define("THRESHOLD_90", 90);
define("THRESHOLD_70", 70);
define("THRESHOLD_30", 30);
define("THRESHOLD_20", 20);
define("THRESHOLD_1", 1);	//	Pengecekan jika nilainya 0