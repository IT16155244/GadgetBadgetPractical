/**
 * 
 */

$(document).ready(function() {
	if($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//Validation
function validateitemForm() {
	
	// First Name
	if($("#firstName").val().trim() == "") {
		return "Insert First Name";
	}
	
	// Last Name
	if($("#lastName").val().trim() == "") {
		return "Insert Last Name";
	}
	
	// DOB
	if($("#dateOfBirth").val().trim() == "") {
		return "Insert Date Of Birth";
	}
	
	// address
	if($("#address").val().trim() == "") {
		return "Insert Address";
	}
	
	// tNo
	if($("#telephoneNo").val().trim() == "") {
		return "Insert Telephone No";
	}
	
	// Is tNo numerical value
	var tmptNo = $("#telephoneNo").val().trim();
	if(!$.isNumeric(tmptNo)) {
		return "Insert Numerical Value for the telephone No";
	}
	
	// user name
	if($("#userName").val().trim() == "") {
		return "Insert Username";
	}
	
	// password
	if($("#password").val().trim() == "") {
		return "Insert Password";
	}
	
	// email
	if($("#email").val().trim() == "") {
		return "Insert Email";
	}
	
	// User type
	if($("#userType").val() == "0") {
		return "Select User Type";
	}
	
	return true;
}

function getUserType() {
	var userType =  getUserType($("#userType").val().trim());
	
	switch (userType) {
		case "1":
			userType = "Buyer";
			break;
		
		case "2":
			userType = "Researcher";
			break;
		
		case "3":
			userType = "Fund Riser";
			break;
	}
	
	return userType;
}

//Save button
$(document).on("click", "#btnSave", function(event) {
	// Clear Alerts field
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form Validation
	var status = validateitemForm();
	if(status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid
	var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "UserManagementAPI",
		type : type,
		data : $("#formUser").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onUserSaveComplete(response.responseText, status);
		}
	});
});

function onUserSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidUserIDSave").val("");
	$("#formUser")[0].reset();
}

//Update button
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidUserIDSave").val($(this).data("userid"));
	$("#firstName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#lastName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#dateOfBirth").val($(this).closest("tr").find('td:eq(2)').text());
	$("#telephoneNo").val($(this).closest("tr").find('td:eq(3)').text());
	$("#userName").val($(this).closest("tr").find('td:eq(4)').text());
	$("#password").val($(this).closest("tr").find('td:eq(5)').text());
	$("#email").val($(this).closest("tr").find('td:eq(6)').text());
	$("#userType").val($(this).closest("tr").find('td:eq(7)').text());
});

// Delete button
$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
	{
		url : "UserManagementAPI",
		type : "DELETE",
		data : "userID=" + $(this).data("userid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


