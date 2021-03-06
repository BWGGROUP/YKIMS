/**
 * dropload
 * 西门
 * 0.3.0(150410)
 */

;(function($){
    'use strict';
    $.fn.dropload = function(options){
        return new MyDropLoad(this, options);
    };
    var MyDropLoad = function(element, options){
        var me = this;
        me.$element = $(element);
        me.insertDOM = false;
        me.loading = false;
        me.isLock = false;
        me.init(options);
    };

    // 初始化
    MyDropLoad.prototype.init = function(options){
        var me = this;
        me.opts = $.extend({}, {
            domUp : {                                                            // 上方DOM
                domClass   : 'dropload-up',
                domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
                domUpdate  : '<div class="dropload-update">↑释放更新</div>',
                domLoad    : '<div class="dropload-load">○加载中...</div>'
            },
            domDown : {                                                          // 下方DOM
                domClass   : 'dropload-down',
                domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
                domUpdate  : '<div class="dropload-update">↓释放加载</div>',
                domLoad    : '<div class="dropload-load">○加载中...</div>'
            },
            distance : 50,                                                       // 拉动距离
            loadUpFn : '',                                                       // 上方function
            loadDownFn : ''                                                      // 下方function
        }, options);

        // 绑定触摸
        me.$element.on('touchstart',function(e){
            if(!me.loading && !me.isLock){
                fnTouches(e);
                fnTouchstart(e, me);
            }
        });
        me.$element.on('touchmove',function(e){
            if(!me.loading && !me.isLock){
                fnTouches(e, me);
                fnTouchmove(e, me);
            }
        });
        me.$element.on('touchend',function(){
            if(!me.loading && !me.isLock){
                fnTouchend(me);
            }
        });
    };
    MyDropLoad.prototype.Destroy= function () {
        var me=this;
        me.opts.domDown='';
        me.opts.domUp='';
        me.lock();
    };
    // touches
    function fnTouches(e){
        if(!e.touches){
            e.touches = e.originalEvent.touches;
        }
    }

    // touchstart
    function fnTouchstart(e, me){
        me._startY = e.touches[0].pageY;
        me._loadHeight = me.$element.height();
        me._childrenHeight = me.$element.children().height();
        me._scrollTop = me.$element.scrollTop();

    }

    // touchmove
    function fnTouchmove(e, me){
        me._curY = e.touches[0].pageY;
        me._moveY = me._curY - me._startY;

        if(me._moveY > 0){
            me.direction = 'down';
        }else if(me._moveY < 0){
            me.direction = 'up';
        }

        var _absMoveY = Math.abs(me._moveY);

        // 加载上方
        if(me.opts.loadUpFn != '' && me._scrollTop <= 0 && me.direction == 'down'){
            e.preventDefault();
            if(!me.insertDOM){
                me.$element.prepend('<div class="'+me.opts.domUp.domClass+'"></div>');
                me.insertDOM = true;
            }

            me.$domUp = $('.'+me.opts.domUp.domClass);
            fnTransition(me.$domUp,0);

            // 下拉
            if(_absMoveY <= me.opts.distance){
                me._offsetY = _absMoveY;
                me.$domUp.html('').append(me.opts.domUp.domRefresh);
                // 指定距离 < 下拉距离 < 指定距离*2
            }else if(_absMoveY > me.opts.distance && _absMoveY <= me.opts.distance*2){
                me._offsetY = me.opts.distance+(_absMoveY-me.opts.distance)*0.5;
                me.$domUp.html('').append(me.opts.domUp.domUpdate);
                // 下拉距离 > 指定距离*2
            }else{
                me._offsetY = me.opts.distance+me.opts.distance*0.5+(_absMoveY-me.opts.distance*2)*0.2;
            }

//            me.$domUp.css({'height': me._offsetY});
        }
        // 加载下方
        if(me.opts.loadDownFn != '' && me._childrenHeight <= (me._loadHeight+me._scrollTop) && me.direction == 'up'){
            e.preventDefault();
            if(!me.insertDOM){
                me.$element.append('<div class="'+me.opts.domDown.domClass+'"></div>');
                me.insertDOM = true;
            }

            me.$domDown = $('.'+me.opts.domDown.domClass);
//            fnTransition(me.$domDown,0);
            if( me._loadHeight> me._childrenHeight){
                me.$domDown.css("bottom","0");
            }else{

            }
            // 上拉
            if(_absMoveY <= me.opts.distance){
                me._offsetY = _absMoveY;
                me.$domDown.html('').append(me.opts.domDown.domRefresh);
                // 指定距离 < 上拉距离 < 指定距离*2
            }else if(_absMoveY > me.opts.distance && _absMoveY <= me.opts.distance*2){
                me._offsetY = me.opts.distance+(_absMoveY-me.opts.distance)*0.5;
                me.$domDown.html('').append(me.opts.domDown.domUpdate);
                // 上拉距离 > 指定距离*2
            }else{
                me._offsetY = me.opts.distance+me.opts.distance*0.5+(_absMoveY-me.opts.distance*2)*0.2;
            }

            me.$domDown.css({'height': me._offsetY});
            me.$element.scrollTop(me._offsetY+me._scrollTop);
        }
    }

    // touchend
    function fnTouchend(me){
        var _absMoveY = Math.abs(me._moveY);
        if(me.insertDOM){
            if(me.direction == 'down'){
                me.$domResult = me.$domUp;
                me.domLoad = me.opts.domUp.domLoad;
            }else if(me.direction == 'up'){
                me.$domResult = me.$domDown;
                me.domLoad = me.opts.domDown.domLoad;
            }
            if(!me.loading){
                me.loading = true;
                if(_absMoveY > me.opts.distance){
                    me.$domResult.html(me.domLoad);
                    me.$domResult.animate({height:me.opts.distance},300, function () {
                        fnCallback(me);
                    })



                }else{

                    me.$domResult.css({'height':'0'}).on('webkitTransitionEnd transitionend',function(){
                        me.insertDOM = false;
                        $(this).remove();
                    });
                }
                me._moveY = 0;
            }
//            fnTransition(me.$domResult,300);
        }
    }

    // 回调
    function fnCallback(me){
        me.loading = true;
        if(me.opts.loadUpFn != '' && me.direction == 'down'){
            me.opts.loadUpFn(me);
        }else if(me.opts.loadDownFn != '' && me.direction == 'up'){
            me.opts.loadDownFn(me);
        }
    }

    // 锁定
    MyDropLoad.prototype.lock = function(){

        var me = this;
        me.isLock = true;

    };

    // 解锁
    MyDropLoad.prototype.unlock = function(){
        var me = this;
        me.isLock = false;
    };

    // 重置
    MyDropLoad.prototype.resetload = function(){
        var me = this;
        if(!!me.$domResult){
            me.$domResult.animate({height:0},300, function () {
                $(this).remove();
                me.loading = false;
                me.insertDOM = false;
            });

        }
    };

    // css过渡
    function fnTransition(dom,num){
        dom.css({
            '-webkit-transition':'height '+num+'ms',
            'transition':'height '+num+'ms'
        });
    }
})(window.Zepto || window.jQuery);
