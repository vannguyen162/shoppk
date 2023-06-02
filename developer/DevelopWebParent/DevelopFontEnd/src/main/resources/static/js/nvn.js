
$(document).ready(function() {
   $('.layPop').each(function() {
     var id = $(this).attr('id');

     // Check if popup has already been closed permanently
     if (getCookieValue("poppupvalue_" + id)) {
       $(this).hide();
     }

     // Add click handlers to popup buttons
     $(this).find('.permanently-closed').on('click', function(evt) {
       evt.preventDefault();
       createCookie("poppupvalue_" + id, "true", 86400); // Set cookie expiration time to 1 day (86400 seconds)
       $(this).closest('.layPop').hide();
     });

     $(this).find('.close-pop').on('click', function(evt) {
       evt.preventDefault();
       $(this).closest('.layPop').hide();
     });
   });
});

function getCookieValue(cookieName) {
  var cookies = document.cookie.split(';');
  for (var i = 0; i < cookies.length; i++) {
    var cookie = cookies[i].trim();
    if (cookie.startsWith(cookieName + "=")) {
      return cookie.substring(cookieName.length + 1);
    }
  }
  return null;
}

function createCookie(cookieName, value, maxAge) {
  var date = new Date();
  date.setTime(date.getTime() + (maxAge * 1000 * 7));
  var expires = "expires=" + date.toUTCString();
  document.cookie = cookieName + "=" + value + ";" + expires + ";" + contextPath;
}
