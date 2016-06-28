
(function($){
    'use strict';
    var CommonAjax=function(element,option)
    {
        if(typeof element ==='undefined')
            throw 'Can\'t find render element,please check your param';
        var list=$(element);
        if(!!list.length&&list.length>=2)
        {
            $.each(list,function(index,item)
            {
                return new CommonAjax($(item),option);
            })
            return;
        }
        if(typeof(list.length)!='undefined'&&list.length==0)
            return false;
        var defaults=
        {
            url:'',
            type:'post',
            redirect_url:'',
            timeout:0,
            data:{},
            before_send:null,
            success_callback:null,
            error_callback:null
        }
        if(typeof(option)=='undefined')
            option={};
        for (var def in defaults) {
            if (typeof option[def] === 'undefined')
            {
                option[def] = defaults[def];
            }
            else if (typeof option[def] === 'object')
            {
                for (var deepDef in defaults[def])
                {
                    if (typeof option[def][deepDef] === 'undefined')
                    {
                        option[def][deepDef] = defaults[def][deepDef];
                    }
                }
            }
        }
        var self=this;
        var $target=$(element);
        var url=$target.attr('data-url');
        var type=$target.attr('data-type');
        var redirect_url=$target.attr('data-redirect-url');
        //var timeout=$target.attr('data-refresh-time');
        option.url=url?url:option.url;
        option.type=type?type:option.type;
        option.redirect_url=redirect_url?redirect_url:option.redirect_url;
       
        self._init=function()
        {
            $target.on('click',function()
            {
                if($target.attr('disabled'))
                    return;
                //before send
                var before=true;
                if(typeof(option.before_send)=='function')
                {
                    before=option.before_send(option);
                    if(typeof(before)=='undefined')
                        before=true;
                }
                //阻断事件
                if(!before)
                    return;
                 //判断type
                self._elementDisabled($target,true);
                $.ajax({
                    url:option.url,
                    type:option.type,
                    data:option.data,
                    xhrFields: {
                      withCredentials: true
                    },
                    success:function(data)
                    {
                        // if(typeof(data)=='object'&&typeof(data.url)!='undefined'&&$.trim(data.url)!='')
                        // {
                        //     self._redirect($target,data.url);
                        //     return;
                        // }
                        if(typeof(data)=='string')
                            data=JSON.parse(data);
                        if(data&&data.status==0)
                        {
                            self._elementDisabled($target,false);

                            if(typeof(option.success_callback)=='function')
                            {
                                option.success_callback(data,$target);
                            }
                            if(typeof(redirect_url)!='undefined'&&$.trim(redirect_url)!='')
                            {
                                self._redirect($target,redirect_url);
                                return ;
                            }
                        }
                        else
                        {
                            self._elementDisabled($target,false);
                            self._alert(data.errorMsg,'danger');
                        }
                       
                    },
                    error:function(data)
                    {
                        if(typeof(data)=='string')
                            data=JSON.parse(data);
                        self._elementDisabled($target,false);
                        if(typeof(option.error_callback)=='function')
                        {
                            option.error_callback(data,$target);
                        }
                        //self._redirect($target);
                    }
                })
            })
        }
        self._init();
    };

    CommonAjax.prototype._redirect=function(element,url)
    {
        var timeout=element.attr('data-refresh-time');
        if(typeof(timeout)=='undefined'||timeout==''||parseInt(timeout)==0)
        {
            timeout=0;
        }
        if (typeof(url)!='undefined' && url != '') {
            window.setTimeout(function () {
                window.location.href =url;
            },parseInt(timeout));
        }
    }
    CommonAjax.prototype._elementDisabled=function(element,disabled)
    {
        if(typeof(disabled)=='undefined'||''== $.trim(disabled)||!disabled)
        {
            element.attr('disabled', false);
        }
        else
        {
            element.attr('disabled', 'disabled');
        }
    }

    CommonAjax.prototype._alert=function(message,type)
    {
        
        // var el=$('<div class="steel-alert"><div class="bg-'+type+'">'+message+'</div></div>').appendTo('body');
        // setTimeout(function()
        // {
        //    el.remove();
        // },2000);
         $('#return_info').html(data.errorMsg);
    }

    window.CommonAjax=CommonAjax;
})(window.jQuery);







