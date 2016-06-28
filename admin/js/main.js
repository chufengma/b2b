var menuTitleTemple = "<h4>{title}</h4>";
var subMenuTemple = "<li><a href='{url}'>{title}</a></li>";
var currentMenuTemple = "<li class='active'><a href='#'>{title}<span class='sr-only'>(current)</span></a></li>";
var menuHtml = "";

var currentPage = getQueryString("page");
if (!currentPage) {
   currentPage = "main";
}

$.getJSON("./menu.js", function(menus) {
        menuHtml += "<div class='col-sm-3 col-md-2 sidebar'>";
        menus.forEach(function(menuItem) {
              menuHtml += bindData(menuTitleTemple, {"title" : menuItem.title});
              menuHtml += "<ul class='nav nav-sidebar'>";
              menuItem.subMenus.forEach(function(subMenu) {
                 if(subMenu.url == currentPage) {
                    var subValue = bindData(currentMenuTemple, {"title":subMenu.name});
                    menuHtml += subValue;
                 } else {
                    var subValue = bindData(subMenuTemple, {"title":subMenu.name, "url": ("./demo.html?page=" + subMenu.url)});
                    menuHtml += subValue;
                 }
              });
              menuHtml += "</ul>";
        });
        menuHtml += "</div>";
        $(".row").prepend(menuHtml);
        $("#content").load(currentPage + ".html");
});


function getQueryString(name) {
   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);
   if(r!=null)return  unescape(r[2]); return null;
}

function bindData(temple, holderValues) {
    var templeValue = temple;
    for(var holder in holderValues) {
        if (holderValues[holder]) {
            templeValue = templeValue.replace("{" + holder  + "}", holderValues[holder]);
        }
    }
    return templeValue;
}
