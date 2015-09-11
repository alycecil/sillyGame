function getTemplateAjax(path, callback) {
    var source;
    var template;

    $.ajax({
      url: path,
        success: function(data) {
          source    = data;
          template  = Handlebars.compile(source);

          //execute the callback if passed
          if (callback){ 
          	callback(template);
          }else{
          	$('head').append(template);
          }
        }
    });
}


function getPartialAjax(path, name) {
    var source;
    var template;

    $.ajax({
      url: path,
        success: function(data) {
          Handlebars.registerPartial(name, data)
        }
    });
}


function registerPartials(){
	getPartialAjax('/static/handlebar/map.handlebars','map')
	getPartialAjax('/static/handlebar/tile.handlebars','tile')
}