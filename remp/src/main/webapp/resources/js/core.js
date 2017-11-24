onload = function () {
	$("#rental").hide(0);
	$("#asset").hide(0);
	$("#EIS").hide(0);
	$("#member").hide(0);
}

function getNavSubMenu(openId) {
	$("#rental").hide(0);	
	$("#asset").hide(0);
	$("#EIS").hide(0);
	$("#member").hide(0);
	$("#"+openId).show(500); 
}
//비밀번호변경 확인
function checkPw(){
	if($("#newpw").val() != $("#checkpw").val()){
		alert("입력하신 새 비밀번호 불일치");
		return false;
	} else {
		document.changepwform.submit();
	}
}

//rentalDetail 품목 아이디 가져오기
function getItemId(element){
	var itemId = $(element).data("code");
	location.href = "rentaldetail.do?itemId="+itemId;
}

//rental품목 아이디 가져오기
function getId(element){
	var itemId = $(element).data("code");
	location.href = "rental.do?itemId="+itemId;
}

//결제수단  카드선택
function showCard(){
	$("#paymentCard").css("display","block");
	$("#paymentCash").css("display","none");
}

//결제수단 현금선택
function showCash(){
	$("#paymentCard").css("display","none");
	$("#paymentCash").css("display","block");
}

//렌탈 기간 설정
function setDate(element){
	var startDate = $(element).val().split("-");
	var endDate = new Date(parseInt(startDate[0]) + 3, parseInt(startDate[1]), parseInt(startDate[2])-1);
	console.log(endDate.getFullYear());
	$("#end").val();
	
}