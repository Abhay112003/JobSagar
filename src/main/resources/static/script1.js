function checkPassword(){
  const pass1=document.getElementById("password").value;
  const pass2=document.getElementById("password2").value;
  if(pass1===pass2)
  {
    console.log(pass1);
    console.log(pass2);
    return true;
  }
  else
  {
   alert("Password do not match");
    return false;
  }

}