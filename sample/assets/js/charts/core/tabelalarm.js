<script>
var alarm_ID;
function reset(){
	table.destroy();
    $("#example").empty();
}
$(document).ready(function() {
			$.ajax({
			url     : "./charts/core/koneksi_tabelalarm.php",
			type    : "POST",             // Type of request to be send, called as method
			cache   : false,             // To unable request pages to be cached
			data	: {alarm_ID : "MSS"},
			success : function(data)   // A function to be called if request succeeds
			{
				//alert("Pusing : "+data);
				var hasil = JSON.parse(data);
				//alert("Cek ini : "+hasil);
				table = $('#example').DataTable( {
					"lengthMenu": [ [5, 10, 25, 50, -1], [5, 10, 25, 50, "All"] ],
					scrollY : true,
					scrollX : true,
					data    : hasil,
					select  : true,
					columns : [
						{ title: "No" },
						{ title: "NEname" },
						{ title: "Severity" },
						{ title: "AlarmTime" },
						{ title: "AlarmID" },
						{ title: "AlarmName" }
					]
				});
			}
		});
	
	$(document).on("click", ".btnx", function(e){
		
		alarm_ID = $(this).val();
		reset();
		
		$.ajax({
			url     : "./charts/core/koneksi_tabelalarm.php",
			type    : "POST",             // Type of request to be send, called as method
			cache   : false,             // To unable request pages to be cached
			data	: {alarm_ID : alarm_ID},
			success : function(data)   // A function to be called if request succeeds
			{
				var hasil = JSON.parse(data);
				table = $('#example').DataTable( {
					"lengthMenu": [ [5, 10, 25, 50, -1], [5, 10, 25, 50, "All"] ],
					scrollY : true,
					scrollX : true,
					data    : hasil,
					select  : true,
					columns : [
						{ title: "No" },
						{ title: "NEname" },
						{ title: "Severity" },
						{ title: "AlarmTime" },
						{ title: "AlarmID" },
						{ title: "AlarmName" }
					]
				});
			}
		});
	});
} );
</script>