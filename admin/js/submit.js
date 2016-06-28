/*
 取消表单提交的默认行为
 */
$(function(){
    $("body").on('submit',"form.submit",function(){
        if(typeof(this)=='undefined')
            return false;
        //获得当前表单对象
        var self = $(this);
        //判断表单验证是否通过
        if(!self.valid()) {
            //$validate.focusInvalid();
            return false;
        }
        var redirect=self.attr('data-redirect-url');
        var timeout=self.attr('data-refresh-time');
        var action=self.attr('action');
        //重定向
        self._redirect=function(data)
        {
           
            if(typeof(timeout)=='undefined'||timeout==''||parseInt(timeout)==0)
            {
                timeout=2000;
            }
            if (typeof(data.url)!='undefined' && $.trim(data.url)!= '') {
                window.setTimeout(function () {
                    window.location.href = data.url;
                },parseInt(timeout));
                return ;
            }
            if (typeof(redirect)!='undefined' && $.trim(redirect)!= '') {
                window.setTimeout(function () {
                    window.location.href = redirect;
                },parseInt(timeout));
            }
        }
        //禁用/解除表单禁用
        self._formDisabled=function(disabled)
        {
            if(typeof(disabled)=='undefind'||''== $.trim(disabled)||!disabled)
            {
                $(self).prop('disabled', '');
                $(self).find('button[type="submit"]').prop('disabled', '');
            }
            else
            {
                $(self).prop('disabled', 'disabled');
                $(self).find('button[type="submit"]').prop('disabled', 'disabled');
            }
        }
        //var formData = new FormData(self[0]); 
        //通过验证提交表单 提交过程禁用表单
        self._formDisabled(true);
        $.ajax
        ({
            url:action,
            type:'post',
            dataType: 'json',
            data:self.serialize(),
            xhrFields: {
                withCredentials: true
            },
            success:function(data)
            {
                if(typeof(data)=='string')
                            data=JSON.parse(data);
                //如果data中返回url则跳转到该路径
                if(data&&data.status==0)
                {
                    self._redirect(redirect);
                }
                else
                {
                    self._formDisabled(false);
                   //  var el=$('<div class="steel-alert"><div class="bg-danger">'+data.errorMsg+'</div></div>').appendTo('body');
                   // setTimeout(function()
                   //  {
                   //     el.remove();
                   //  },2000);
                   $('#return_info').html(data.errorMsg);
                }
                //如果表单提交字段不对 表单为脏
                // if (typeof(self.dirtyForms) == 'function' && self.dirtyForms('isDirty')) {
                //     self.dirtyForms('setDirty');
                // }
                //self._formDisabled(false);
            },
            error:function(data)
            {
                if(typeof(data)=='string')
                            data=JSON.parse(data);
                //设置表单为验证错误
                self.error();
                //self._redirect(data);
                self._formDisabled(false);
            }
        });
        //取消表单默认提交事件
        return false;
    });
});

