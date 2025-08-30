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

  function CheckErrorMessage()
  {
    const name=document.getElementById('name').value;
    const contact=document.getElementById('contact').value;
   if(name.length>0)
   {
     document.getElementById('errorpara').innerHTML="";
   }
   if(contact.length>0)
   {
   document.getElementById('errorcontact').innerHTML="";
   }


  }

}
  function checkpassword(){
  const pass1=document.getElementById('password').value;
  const pass2=document.getElementById('confirm-password').value;
  const err=document.getElementById('error');
  if(pass1===pass2)
  {
  console.log(pass1);
  console.log(pass2);
    err.innerHTML="";
   return true;
  }
  else
  {
   console.log(pass1);
    console.log(pass2);
  err.innerHTML="Password do not match";
  return false;
  }

  }
