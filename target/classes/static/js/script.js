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
