/**
 * Created by shbs-tp001 on 15-9-30.
 */
(function ($) {
    var def={
        inType:"right"
    };
    var animate=function(a){
        var action={};
        switch(a){
            case "right":
                action.inType="in-right";
                action.out="out-right";
                return action;
            break;
            case "left":
                action.inType="in-left";
                action.out="out-left";
                return action;
            break;
        }
    };
    $.fn.showpage=function(opt){
        def= $.extend({},def,opt);
        var config= function (obj) {

            $(obj).addClass("Page "+animate(def.inType).inType);
            $("html,body").css({width:"100%",height:"100%",overflow:"hidden"});
            $(obj).one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend',function () {
                $(obj).css({left:"0"});
            });

        };
        var hidepage= function () {
            $("html,body").css({height: "", overflow: ""});
            $this=this;

            $($this.$this).removeClass(animate($this.inType).inType).addClass(animate($this.inType).out).bind('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                $($this.$this).removeClass("Page "+animate($this.inType).out).removeAttr("style");
                $($this.$this).unbind();
            });

        };
        config(this);
        def.$this=$(this);
        def.hidepage=hidepage;
        return def;
    };

})(jQuery);