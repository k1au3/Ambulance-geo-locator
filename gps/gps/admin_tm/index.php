<?php
session_start();
$conn = mysqli_connect("localhost","root","","kickstart_project");
if (!$conn) {
	die ('Failed to connect to MySQL: ' . mysqli_connect_error());	
}
//session

if(isset($_POST['Approve'])){
if(isset($_POST['checkbox']))
{
    $checkbox = $_POST['checkbox'];
    $id = "('" . implode( "','", $checkbox ) . "');" ;
    $sql="UPDATE tasks SET status = 'Approved' WHERE id IN $id";
    $result = mysqli_query($conn,$sql) or die(mysql_error());
}
}

if (isset ($_POST['populate']) && isset ($_POST['month']) && isset ($_POST['year']) && isset ($_POST['user'])){
	
	
	//$month=$_REQUEST["month"];
	//$year=$_REQUEST["year"];
	$user=$_REQUEST["user"];
	//$country=$_REQUEST["country"];

	
	//echo $form."  ".$stream.";

// $sql = "SELECT e.*,t.* 
// 		FROM employee e INNER JOIN tasks t ON e.email=t.email WHERE Year(tdate) = '$year' and Month(tdate) = '$month'  and sno='$user' ORDER BY id DESC";
 		$sql = "SELECT e.*,t.* 
		FROM employee e INNER JOIN tasks t ON e.email=t.email WHERE   sno='$user' ORDER BY id DESC";
		
$query = mysqli_query($conn, $sql);

if (!$query) {
	die ('SQL Error: ' . mysqli_error($conn));
}
}else if(isset ($_POST['invoicable']) && isset ($_POST['month']) && isset ($_POST['year'])){
    $month=$_REQUEST["month"];
	$year=$_REQUEST["year"];

	
	//echo $form."  ".$stream.";

// $sql = "SELECT e.*,t.* 
// 		FROM employee e INNER JOIN tasks t ON e.email=t.email WHERE Year(tdate) = '$year' and Month(tdate) = '$month' and sno='$user' ORDER BY id DESC";
//$query = mysqli_query($conn, $sql);
//$result = mysqli_query($conn, $query) or die(mysqli_error($conn));
                                    //$count = mysqli_num_rows($results);
                                    

if (!$query) {
	die ('SQL Error: ' . mysqli_error($conn));
}
}
	
$message="";
if(!empty($_POST["login"])) {
	$result = mysqli_query($conn,"SELECT * FROM users WHERE username='" . $_POST["user_name"] . "' and password = '". $_POST["password"]."'");
	$row  = mysqli_fetch_array($result);
	if(is_array($row)) {
	$_SESSION["user_id"] = $row['user_id'];
	$_SESSION["level"]=$row['level'];
	
	} else {
	$message = "Invalid Username or Password!";
	}
}
if(!empty($_POST["logout"])) {
	$_SESSION["user_id"] = "";
	$_SESSION["level"]="";
	session_destroy();
}
?>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  
  <script type="text/javascript">
<!--//checkboxes
function un_check(){
for (var i = 0; i < document.frmactive.elements.length; i++) {
var e = document.frmactive.elements[i];
if ((e.name != 'allbox') && (e.type == 'checkbox')) {
e.checked = document.frmactive.allbox.checked;
}
}
}
//-->
</script>
<script type="text/javascript">
//print
function printDiv() {
    document.getElementById('footer').style.display = "block";
         var divToPrint = document.getElementById('task');
    var htmlToPrint = '' +
        '<style type="text/css">' +
        'table th, table td {' +
        'border:1px solid #000;' +
        'padding;0.5em;' +
        'text-align:center;'+
        '}' +
        '#task{'+
        'text-align:center;'
        +'}'+
        '#ptable{'+
        'margin-left: auto;'+
        'margin-right: auto;'+
        'width:100%;'+
        +'}'+
        '#hf{'+
    'text-decoration: underline;'
+'}'+
        '</style>';
    htmlToPrint += divToPrint.outerHTML;
    newWin = window.open("");
    newWin.document.write(htmlToPrint);
    newWin.print();
    newWin.close();
       }
</script>
<title>:Kickstart Time Tracker</title>
<style>
html {
  height: 100%;
  box-sizing: border-box;
}

*,
*:before,
*:after {
  box-sizing: inherit;
}

body {
  position: relative;
  margin: 0;
  padding-bottom: 6rem;
  min-height: 100%;
}
.pfooter {
  position: absolute;
  right: 0;
  bottom: 2px;
  left: 0;
  padding: 1rem;
  background-color:#b71c1c;
  text-align: center;
  color:white;
  border-radius:25px;
}
@page { size: auto;  margin: 0mm; }
#frmLogin { 
	padding: 20px 60px;
	background:white;
	color:#000000;
	display: inline-block;
	border-radius: 4px;
	margin:auto;
	vertical-align: middle;
	margin-top:auto;
}
.field-group { 
	margin:15px 0px;
	color:#000000;
}
.input-field {
	padding: 8px;width: auto;
	border: #A3C3E7 1px solid;
	border-radius: 4px;
	color:#000000;
	text-align:center;
}
.form-submit-button {
	background: #65C370;
	border: 0;
	padding: 8px 20px;
	border-radius: 4px;
	color: #FFF;
	text-transform: uppercase; 
}
.member-dashboard {
	padding: 20px;
	background: #993300;
	color: white;
	border-radius: 25px;
	text-align:right;
	font-size: 20px;
	margin-top:2px;
}
.logout-button {
	color:white;
	text-decoration: none;
	background:#ff0000;
	border: none;
	padding: 0px;
	cursor: pointer;
	font-weight:bold;
	border-radius:5px;
}
.error-message {
	text-align:center;
	color:#FF0000;
}
.demo-content label{
	width:auto;
}
table {
			margin: auto;
			font-family: "Lucida Sans Unicode", "Lucida Grande", "Segoe Ui";
			font-size: 12px;
		}

		h1 {
			margin: 0px auto 0;
			text-align: center;
			text-transform: uppercase;
			font-size: 20px;
		}

		table td {
			transition: all .5s;
		}
		
		/* Table */
		.data-table {
			border-collapse: collapse;
			font-size: 14px;
			min-width: 537px;
		}

		.data-table th, 
		.data-table td {
			border: 1px solid #e1edff;
			padding: 7px 17px;
		}
		.data-table caption {
			margin: 7px;
		}

		/* Table Header */
		.data-table thead th {
			background-color: #508abb;
			color: #FFFFFF;
			border-color: #6ea1cc !important;
			text-transform: uppercase;
		}

		/* Table Body */
		.data-table tbody td {
			color: #353535;
		}
		.data-table tbody td:first-child,
		.data-table tbody td:nth-child(4),
		.data-table tbody td:last-child {
			text-align: center;
		}

		.data-table tbody tr:nth-child(odd) td {
			background-color: #f4fbff;
		}
		.data-table tbody tr:hover td {
			background-color: #ffffa2;
			border-color: #ffff0f;
		}

		/* Table Footer */
		.data-table tfoot th {
			background-color: #e5f5ff;
			text-align: right;
		}
		.data-table tfoot th:first-child {
			text-align: left;
		}
		.data-table tbody td:empty
		{
			background-color: #ffcccc;
		}
		
		.row:after { clear: both; }

.row:before, .row:after {
  content: " ";
  display: table;  }

@media (min-width: 768px) {
  .col { 
    float: left;
    width: 32.5%;
    font-size:20px;}
}
</style>
</head>
<body>
<div>
<div style="display:block;margin:0px auto;">
<?php if(empty($_SESSION["user_id"])) { ?>
<center>
<form action="" method="post" id="frmLogin">
    <b><p class="center login-form-text">Kickstart Time Tracking System</p></b>
            <img src="img/amtech.jpg" alt="" class="responsive-img valign profile-image-login" style="width:200px;height:80px;">
	<div class="error-message"><?php if(isset($message)) { echo $message; } ?></div>	
	<div class="field-group">
		<div><label for="login" color="#000000">Username</label></div>
		<div><input name="user_name" type="text" class="input-field"></div>
	</div>
	<div class="field-group">
		<div><label for="password">Password</label></div>
		<div><input name="password" type="password" class="input-field"> </div>
	</div>
	<div class="field-group">
		<div><input type="submit" name="login" value="Login" class="form-submit-button"></span></div>
	</div>       
</form>
<?php 
} else { 
$result = mysqlI_query($conn,"SELECT * FROM users WHERE user_id='" . $_SESSION["user_id"] . "'");
$row  = mysqli_fetch_array($result);
?>
<center>
<form action="" method="post" id="frmLogout" name="frmactive">
<div class="member-dashboard"><input type="submit" <?php if($_SESSION["level"]!="Administrator") { echo 'style="display:none;"'; } ?> name="manageappu" style="background:#66BB6A;color:white;border-radius:10px;font-family:Times New Roman;font-size:18px; border-color:#66BB6A;" formaction="appusers/" value="&nbsp;&nbsp;Manage App Users&nbsp;&nbsp;"><input type="submit" <?php if($_SESSION["level"]!="Administrator") { echo 'style="display:none;"'; } ?> name="manageu" style="background:#66BB6A;color:white;border-radius:10px;font-family:Times New Roman;font-size:18px; border-color:#66BB6A;" formaction="records/" value="&nbsp;&nbsp;Manage Users&nbsp;&nbsp;"><h1>Kickstart time tracking </h1><hr>Welcome <?php echo ucwords($row['username']); ?>, <input type="submit" name="logout" value="&nbsp;&nbsp;Logout&nbsp;&nbsp;" class="logout-button"></div>
<div id= "filter" style = "width:auto;background:#993300;border:solid;border-radius:25px;color:white;">
<?php
//Connect to our MySQL database using the PDO extension.
$pdo = new PDO('mysql:host=localhost;dbname=kickstart_project', 'root', '');
 
//Our select statement. This will retrieve the data that we want.
$sql = "SELECT sno, name FROM employee";
 
//Prepare the select statement.
$stmt = $pdo->prepare($sql);
 
//Execute the statement.
$stmt->execute();
 
//Retrieve the rows using fetchAll.
$users = $stmt->fetchAll();
 
?>
<br><span <?php if($_SESSION["level"]=="Gmanager") { echo 'style="display:none;"'; } ?>>Username</span>&nbsp;
<select name="user" <?php if($_SESSION["level"]=="Gmanager") { echo 'style="display:none;"'; } ?> id="user" onchange="document.getElementById('selected_text').value=this.options[this.selectedIndex].text">
    <option value="">Select Employee</option>
    <?php foreach($users as $user): ?>
        <option value="<?= $user['sno']; ?>"><?= $user['name']; ?></option>
    <?php endforeach; ?>
</select>
<input type="hidden" name="selected_text" id="selected_text" value="" />
&nbsp;&nbsp;Month&nbsp;
<select name="month" id="month" size='1'>
    <?php
    for ($i = 0; $i < 12; $i++) {
        $time = strtotime(sprintf('%d months', $i));   
        $label = date('F', $time);   
        $value = date('n', $time);
        echo "<option value='$value'>$label</option>";
    }
    ?>
</select>


	&nbsp;&nbsp;Region&nbsp;
	<select>
	<option value="">Select Region</option>
		<?php
		$sql="SELECT region,name FROM employee order by name"; 

		 

foreach ($pdo->query($sql) as $row){//Array or records stored in $row

echo "<option value=$row[name]>$row[region]</option>"; 

}

 echo "</select>";// Closing of list box
?>

	
	
</select>

	&nbsp;&nbsp;Country&nbsp;
	<select>
	<option value="">Select Country</option>
		<?php
		$sql="SELECT country,name FROM employee order by name"; 

		 

foreach ($pdo->query($sql) as $row){//Array or records stored in $row

echo "<option value=$row[name]>$row[country]</option>"; 

}

 echo "</select>";// Closing of list box
?>

	
	
</select>

&nbsp;&nbsp;Year&nbsp;
<?php 
function yearDropdown($startYear, $endYear, $id="year"){ 
    //start the select tag
    echo "<select id=".$id." name=".$id.">n"; 
        //echo each year as an option     
        for ($i=$startYear;$i<=$endYear;$i++){ 
        echo "<option value=".$i.">".$i."</option>n";     
        } 
    //close the select tag 
    echo "</select>"; 
} 
?>

<?php  
 
//call the function 
 
yearDropdown(date('Y'), date('Y')+10);  
 
?>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type=submit <?php if($_SESSION["level"]=="Cmanager") { echo 'style="display:none;"'; } ?> value="View Individual records" class="form-submit-button" name="populate" id="populate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--input type=submit value="#" class="form-submit-button" name="invoicable" id="invoicable"/><br><br-->
</div>
<br>
<div id="viewarea"<?php if(isset($_POST['populate']) || isset($_POST['invoicable'])) { echo 'style="display:block;"'; } ?> style="display:none;">
<div id="task">
    <img src="img/amtech.jpg" alt="" class="responsive-img valign profile-image-login" style="width:200px;height:80px;">
   <h3><label class="form_field"><span id="enam" style="text-transform: uppercase;"><?php
if(isset($_POST['populate'])){
$selected_val =$_POST['selected_text'];  // Storing Selected text In Variable
echo "Employee Name:&nbsp;" .$selected_val;  // Displaying Selected Value
}
?></span></label></h3>
<h3><label class="form_field"><span id="period"><?php
if(isset($_POST['populate'])){
$month_name = date("F", mktime(0, 0, 0, $_POST['month'], 15));
$selected_val =$month_name.',&nbsp;'.$_POST['year'];  // Storing Selected text In Variable
echo "Time sheet for:&nbsp;" .$selected_val;  // Displaying Selected Value
}
?>
<div style="overflow-x:auto;">
	<table class="data-table" id="ptable" style="text-align:center;">
		<caption class="title"></caption>
		<thead>
			<tr>
			    <th>#</th>
			    
				<th>Task date</th>
				
				<th>Remarks</th>
				
				
				<th>Invoiceable</th>

				<th>Phone number</th>

				<th>Physical location</th>
				
				<!--th><input type="checkbox" name="allbox" onclick="un_check(this);" title="Select or Deselct ALL" style="background-color:#ccc;"/></th-->
				
			</tr>
		</thead>
		<tbody>
		<?php
		
		
		while ($row = mysqli_fetch_array($query))
			 
		{
		
        	
              //echo'<tr><td>'.$count.'</td>
                 echo '<td>'.$row['tdate'].'</td>
                                            
                                              

                                
                                             <td>'.$row['remarks'].'</td>
                                             <td>'.$row['invoicable'].'</td>
                              
                                             <td>'.$row['country'].'</td>
                                             <td>'.$row['gps'].'</td>
                                               


                                         
          
            ';
        
            
          

echo '</tr>';
                                           
                                            
 

		}?>
		</tr>
		</tbody>
	</table>
	<center>
   <div class="page-footer">
          <div class="footer-copyright">
            <div class="container" style="display:none;" id="footer"><br>
            AmTech Technologies Plaza, Matasia town. Nairobi, Kenya.<br> P.O. BOX 79701-00200 Nairobi, Direct line +254 20 553300, Mobile: 0734871556, Fax +254 20559867 <br> Email: <a href="mailto:info@amtechafrica.com">info@amtechafrica.com</a> Website: <a href="www.amtechafrica.com">www.amtechafrica.com</a>
            </div>
          </div>
  </div>
  </center>
	</div>
	</div>
<!--<input type="submit" value="Approve" class="form-submit-button" name="Approve" id="Approve"/>&nbsp;&nbsp;-->
<input type="submit" value="&nbsp;&#x1f5b6;&nbsp;" style="background:transparent; font-size:20px; border-radius:10px;color:#993300;" name="Print" id="Print" onclick="printDiv()"/>
</div>
</form>
</center>
</center>
</div>
</div>
<?php } ?>
<center>
<footer class="pfooter" style="background-color:#993300;">
Designed by Kickstart Web Master  &nbsp;&nbsp; © 2018 Kickstart ltd.
  </footer>
  </center>
<!--=======scripts==========-->
</body></html>