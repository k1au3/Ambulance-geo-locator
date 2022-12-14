
<?php
	//include connection file 
	include_once("connection.php");
	
	$db = new dbObj();
	$connString =  $db->getConnstring();

	$params = $_REQUEST;
	
	$action = isset($params['action']) != '' ? $params['action'] : '';
	$empCls = new Employee($connString);

	switch($action) {
	 case 'add':
		$empCls->insertEmployee($params);
	 break;
	 case 'edit':
		$empCls->updateEmployee($params);
	 break;
	 case 'delete':
		$empCls->deleteEmployee($params);
	 break;
	 default:
	 $empCls->getEmployees($params);
	 return;
	}
	
	class Employee {
	protected $conn;
	protected $data = array();
	function __construct($connString) {
		$this->conn = $connString;
	}
	
	public function getEmployees($params) {
		
		$this->data = $this->getRecords($params);
		
		echo json_encode($this->data);
	}
	function insertEmployee($params) {
		$data = array();
		$unique_id = uniqid('', true);
        $hash = $this->getHash($params["password"]);
        $encrypted_password = $hash["encrypted"];
        $salt = $hash["salt"];
		$sql = "INSERT INTO `employee` (unique_id,name,country,email,encrypted_password,design,region,role,salt,created_at) VALUES('" .$unique_id. "','" . $params["fullname"] . "','" . $params["country"] . "', '" . $params["email"] . "','" .$encrypted_password. "','" .$params["design"]. "','" .$params["region"]. "','" .$params["role"]. "','" .$salt. "',now());";
		
		echo $result = mysqli_query($this->conn, $sql) or die ('Failed to connect to MySQL: ' . mysqli_connect_error());
	}
	
	public function getHash($password) {

     $salt = sha1(rand());
     $salt = substr($salt, 0, 10);
     $encrypted = password_hash($password.$salt, PASSWORD_DEFAULT);
     $hash = array("salt" => $salt, "encrypted" => $encrypted);

     return $hash;

}
	
	function getRecords($params) {
		$rp = isset($params['rowCount']) ? $params['rowCount'] : 10;
		
		if (isset($params['current'])) { $page  = $params['current']; } else { $page=1; };  
        $start_from = ($page-1) * $rp;
		
		$sql = $sqlRec = $sqlTot = $where = '';
		
		if( !empty($params['searchPhrase']) ) {   
			$where .=" WHERE ";
			$where .=" ( name LIKE '".$params['searchPhrase']."%' ";    
			$where .=" OR email LIKE '".$params['searchPhrase']."%' ";

			$where .=" OR phone LIKE '".$params['searchPhrase']."%' )";
	   }
	   if( !empty($params['sort']) ) {  
			$where .=" ORDER By ".key($params['sort']) .' '.current($params['sort'])." ";
		}
	   // getting total number records without any search
		$sql = "SELECT * FROM `employee` ";
		$sqlTot .= $sql;
		$sqlRec .= $sql;
		
		//concatenate search sql if value exist
		if(isset($where) && $where != '') {

			$sqlTot .= $where;
			$sqlRec .= $where;
		}
		if ($rp!=-1)
		$sqlRec .= " LIMIT ". $start_from .",".$rp;
		
		
		$qtot = mysqli_query($this->conn, $sqlTot) or die("error to fetch tot user data");
		$queryRecords = mysqli_query($this->conn, $sqlRec) or die("error to fetch user data");
		
		while( $row = mysqli_fetch_assoc($queryRecords) ) { 
			$data[] = $row;
		}

		$json_data = array(
			"current"            => intval($params['current']), 
			"rowCount"            => 10, 			
			"total"    => intval($qtot->num_rows),
			"rows"            => $data   // total data array
			);
		
		return $json_data;
	}
	function updateEmployee($params) {
		$data = array();
		//print_R($_POST);die;
		$sql = "Update `users` set username = '" . $params["edit_username"] . "', email='" . $params["edit_email"]."', password='" . $params["edit_password"] . "' WHERE user_id='".$_POST["edit_uid"]."'";
		
		echo $result = mysqli_query($this->conn, $sql) or die("error to update user data");
	}
	
	function deleteEmployee($params) {
		$data = array();
		//print_R($_POST);die;
		$sql = "delete from `employee` WHERE sno='".$params["sno"]."'";
		
		echo $result = mysqli_query($this->conn, $sql) or die("error to delete user data");
	}
}
?>
	