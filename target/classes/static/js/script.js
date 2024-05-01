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
		
const passwordform = () => {
	document.getElementById()
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