function checkPassword(){
  const pass1=document.getElementById("password").value;
  const pass2=document.getElementById("password2").value;
  const error=document.getElementById("confirmpassword");
  if(pass1===pass2)
  {
    console.log(pass1);
    console.log(pass2);
    error.innerHTML="";
    return true;
  }
  else
  {
    error.innerHTML="Password do not match";
    return false;
  }
}
function seekercheckPassword(){
  const pass1=document.getElementById("password").value;
  const pass2=document.getElementById("password2").value;
  const error=document.getElementById("confirmpassword");
  if(pass1===pass2)
  {
    console.log(pass1);
    console.log(pass2);
    error.innerHTML="";
    return true;
  }
  else
  {
    error.innerHTML="Password do not match";
    return false;
  }
}