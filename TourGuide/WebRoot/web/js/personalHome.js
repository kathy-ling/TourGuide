

function weiXin()
{
	var iphone = GetUrlem("phone");
	window.location.href = "QRcodeScan.html?guidePhone="+iphone;
		
}


function getAllOrders(){
	var iphone = GetUrlem("phone");
	alert("clicked" + iphone);
	window.location.href = "orderFormList.html";
}
