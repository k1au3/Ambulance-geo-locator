<?php
session_start();

define( 'RESTRICTED', true );

if ( defined( 'RESTRICTED' ) ) {
    if ( !isset( $_SESSION["user_id"])|| $_SESSION['level'] != 'Administrator' || !isset( $_POST["manageu"])) {
      header( 'Location: kicksart/admin_tm/' );
      exit();
    }
}
else if (isset( $_SESSION["user_id"])) {
      header( 'Location: kicksart/admin_tm/records/' );
      exit();
    }
// etc

?>
<!DOCTYPE html>
<html>
<head>
    <style>
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
    </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Manage TimeSheet users</title>
<link rel="stylesheet" href="dist/bootstrap.min.css" type="text/css" media="all">
<link href="dist/jquery.bootgrid.css" rel="stylesheet" />
<script src="dist/jquery-1.11.1.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<script src="dist/jquery.bootgrid.min.js"></script>
</head>
<body style="margin:auto;width:80%;">
	<div class="">
      <div class="">
        <div class="">
		<div class="well clearfix">
		    <a href=""><img src="kicksart/admin_tm/img/amtech.png" alt="" class="responsive-img valign profile-image-login" style="width:200px;height:80px;"></a>
			<div class="pull-right"><button type="button" class="btn btn-xs btn-primary" id="command-add" data-row-id="0">
			<span class="glyphicon glyphicon-plus"></span> New User</button></div></div>
			
		<table id="employee_grid" class="table table-condensed table-hover table-striped" width="60%" cellspacing="0" data-toggle="bootgrid">
		     <center><caption class="caption">Current Users</caption></center>
			<thead>
				<tr>
					<th data-column-id="user_id" data-type="numeric" data-identifier="true">User ID</th>
					<th data-column-id="username">Username</th>
					<th data-column-id="email">Email</th>
					<th data-column-id="level">level</th>
          <th data-column-id="region">Region</th>
          <th data-column-id="country">Country</th>
					<th data-column-id="commands" data-formatter="commands" data-sortable="false">Commands</th>
				</tr>
			</thead>
		</table>
		<center><a href="../"stye="font-size:20px;">&#x261b;Go back to homepage&#x2794;</a></center>
    </div>
      </div>
      
    </div>
	
<div id="add_model" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Add User</h4>
            </div>
            <div class="modal-body">
                <form method="post" id="frm_add">
				<input type="hidden" value="add" name="action" id="action">
				<div class="form-group">
                <label class="control-label" for="userlevel">Select User Level</label>
                <select id="userlevel" class="form-control" name="userlevel" minLength="2">
                    <option value="Administrator">Administrator</option>
                    <option value="Gmanager">Global manager</option>
                    <option value="Rmanager">Regional manager</option>
                    <option value="Cmanager">Country manager</option>
                </select>
                </div>
                  <div class="form-group">
                    <label for="username" class="control-label">Username:</label>
                    <input type="text" minLength="3" required class="form-control" id="username" name="username"/>
                  </div>
                  <div class="form-group">
                    <label for="email" class="control-label">Email:</label>
                    <input type="email" minLength="10" required
          placeholder="username@gmail.com" class="form-control" id="email" name="email" pattern=".+@gmail.com"
          title="Please provide only AmTech Technologies ltd. email address"/>
                  </div>
				  
                  <div class="form-group">
                    <label for="region" class="control-label">Region:</label>
                    <input type="text" minLength="3" required class="form-control" id="region" name="region"/>
                  </div>
                  <div class="form-group">
                    <label for="country" class="control-label">Country:</label>
                    <input type="text" minLength="3" required class="form-control" id="country" name="country"/>
                  </div>
                  <div class="form-group">
                    <label for="password" class="control-label">Password:</label>
                    <input type="password" minLength="6" required class="form-control" id="password" name="password" title="minimum of 6 characters"/>
                  </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="btn_add" class="btn btn-primary">Save</button>
            </div>
			</form>
        </div>
    </div>
</div>
<div id="edit_model" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Edit User</h4>
            </div>
            <div class="modal-body">
                <form method="post" id="frm_edit">
				<input type="hidden" value="edit" name="action" id="action">
				<input type="hidden" value="0" name="edit_uid" id="edit_uid">
                  <div class="form-group">
                    <label for="username" class="control-label">Username:</label>
                    <input type="text" class="form-control" id="edit_username" name="edit_username"/>
                  </div>
                  <div class="form-group">
                    <label for="email" class="control-label">Email:</label>
                    <input type="text" class="form-control" id="edit_email" name="edit_email"/>
                  </div>
				  <div class="form-group">
                    <label for="password" class="control-label">Password:</label>
                    <input type="password" class="form-control" id="edit_password" name="edit_password"/>
                  </div>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="btn_edit" class="btn btn-primary">Save</button>
            </div>
			</form>
        </div>
    </div>
</div>
<center>
<footer class="pfooter">
 Designed by Kickstart WebMaster  &nbsp;&nbsp;© 2018 Kickstart ltd.
  </footer>
  </center>
</body>
</html>
<script type="text/javascript">
$( document ).ready(function() {
	var grid = $("#employee_grid").bootgrid({
		ajax: true,
		rowSelect: true,
		post: function ()
		{
			/* To accumulate custom parameter with the request object */
			return {
				id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
			};
		},
		
		url: "response.php",
		formatters: {
		        "commands": function(column, row)
		        {
		            return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.user_id + "\"><span class=\"glyphicon glyphicon-edit\"></span></button> " + 
		                "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.user_id + "\"><span class=\"glyphicon glyphicon-trash\"></span></button>";
		        }
		    }
   }).on("loaded.rs.jquery.bootgrid", function()
{
    /* Executes after data is loaded and rendered */
    grid.find(".command-edit").on("click", function(e)
    {
        //alert("You pressed edit on row: " + $(this).data("row-id"));
			var ele =$(this).parent();
			var g_id = $(this).parent().siblings(':first').html();
            var g_name = $(this).parent().siblings(':nth-of-type(2)').html();
console.log(g_id);
                    console.log(g_name);

		//console.log(grid.data());//
		$('#edit_model').modal('show');
					if($(this).data("row-id") >0) {
							
                                // collect the data
                                $('#edit_uid').val(ele.siblings(':first').html()); // in case we're changing the key
                                $('#edit_username').val(ele.siblings(':nth-of-type(2)').html());
                                $('#edit_email').val(ele.siblings(':nth-of-type(3)').html());
                                $('#edit_password').val(ele.siblings(':nth-of-type(4)').html());
					} else {
					 alert('Now row selected! First select row, then click edit button');
					}
    }).end().find(".command-delete").on("click", function(e)
    {
	
		var conf = confirm('Delete ' + $(this).data("row-id") + ' items?');
					alert(conf);
                    if(conf){
                                $.post('response.php', { user_id: $(this).data("row-id"), action:'delete'}
                                    , function(){
                                        // when ajax returns (callback), 
										$("#employee_grid").bootgrid('reload');
                                }); 
								//$(this).parent('tr').remove();
								//$("#employee_grid").bootgrid('remove', $(this).data("row-id"))
                    }
    });
});

function ajaxAction(action){
				data = $("#frm_"+action).serializeArray();
				$.ajax({
				  type: "POST",  
				  url: "response.php",  
				  data: data,
				  dataType: "json",       
				  success: function(response)  
				  {
					$('#'+action+'_model').modal('hide');
					$("#employee_grid").bootgrid('reload');
				  }   
				});
			}
			
			$( "#command-add" ).click(function() {
			  $('#add_model').modal('show');
			});
			$( "#btn_add" ).click(function() {
			  ajaxAction('add');
			});
			$( "#btn_edit" ).click(function() {
			  ajaxAction('edit');
			});
});
</script>
