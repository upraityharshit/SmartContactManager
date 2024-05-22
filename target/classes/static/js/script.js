console.log("this is script file");

const togglesidebar = () =>{
	if($(".sidebar").is(":visible")){
		//hide the sidebar
		$(".sidebar").css("display","none");
		$(".content .menubtn").css("display","block");
		$(".content").css("margin-left","0%");
	}
	else{
		//show the sidebar
		$(".sidebar").css("display","block");
		$(".content .menubtn").css("display","none");
		$(".content").css("margin-left","20%");
	}
};

function deleteContact(cid) {
			swal({
				title: "Are you sure?",
				text: "Once deleted, you will not be able to recover this Contact!",
				icon: "warning",
				buttons: true,
				dangerMode: true,
			})
				.then((willDelete) => {
					if (willDelete) {
						swal("Poof! Your imaginary file has been deleted!", {
						      icon: "success",
						    });
						
					} else {
						swal("Your contact is safe!");
					}
				});
}
		
const search = () =>{
	
	let query = $("#search-field").val();
	
	if(query==""){
		$(".search-result").hide();
	}
	else{
		
		let url = `http://localhost:8080/search/${query}`;
		
		fetch(url)
		.then((response)=>{
			return response.json();
			
		})
		.then((data)=>{
			console.log(data);
			
			let text = `<div class='list-group'>`;
			
			data.forEach((contact) =>{
				text+= `<a href='/user/${contact.cid}/view-contactProfile' class='list-group-item list-group-item-action'> ${contact.name} </a>`
			});
			
			text+= `</div>`;
			
			$(".search-result").html(text);
			$(".search-result").show();
			
		})
	}	
	
}


var otp=0;

const SendOTP = () => {
	 $('.submit').prop('disabled', true);
    let email = $("#email").val();
    
    if (email === "") {
        $(".verifyotp").hide();
        return;
    }
    
    otp = Math.floor((Math.random() * 1000000) + 1);
    console.log(otp);
    const data = {
        "to": email,
        "subject": "Verification OTP",
        "message": `Your OTP is: ${otp}`
    };
    
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    };
    
    const url = 'https://emailapi-backend.onrender.com/sendemail';
    
    fetch(url, requestOptions)
    .then(response => {
        if (!response.ok) {
            throw new Error('Mail not sent...!');
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        
        let text = `<div class='form-group email mt-3'>`;
        text += `<input type='number' id="otpfield" class='form-control' placeholder='Enter your email OTP' required>`;
        text += `<button type='button' class='btn btn-warning verify' onclick='verify()'>Verify</button>`;
        text += `</div>`;
        
        $(".verifyotp").html(text);
        $(".verifyotp").show();
    })
    .catch(error => {
        console.error('Error:', error);
    });
};

function verify(){
		let otpdata = $("#otpfield").val();
		
		if(otpdata === ""){
			alert("Please Enter the OTP and Verify");
		}
		else{
			if(otp!=otpdata){
				alert("OTP is not matched");
			}
			else{
				alert("Email has been verified...");
				$(".verify").css("background-color", "Green");
				$(".verify").css("color", "white");
				$('.submit').prop('disabled', false);
			}
		}
}
