// basic-N2 [pyltTX4eYW]
(function() {
  $(function() {
    $(".basic-N2").each(function() {
      const $block = $(this);
      const $dim = $block.find('.header-dim');
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 120) {
          $block.addClass("header-top-active");
        } else {
          $block.removeClass("header-top-active");
        }
      });
      // Header Mobile 1Depth Click
      if (window.innerWidth <= 992) {
        $block.find(".header-gnbitem").each(function() {
          const $gnblink = $(this).find(".header-gnblink");
          const $sublist = $(this).find(".header-sublist");
          if ($sublist.length) {
            $gnblink.attr("href", "javascript:void(0);");
          }
        });
      }
      // Mobile Lang
      $block.find('.header-langbtn').on('click', function() {
        $(this).parent().toggleClass('lang-active');
      });
      // Mobile Top
      $block.find('.btn-momenu').on('click', function() {
        $block.addClass('momenu-active');
        $dim.fadeIn();
      });
      $block.find('.btn-close, .header-dim').on('click', function() {
        $block.removeClass('momenu-active');
        $dim.fadeOut();
      });
      // Mobile Gnb
      $block.find('.header-gnbitem').each(function() {
        const $this = $(this);
        const $thislink = $this.find('.header-gnblink');
        $thislink.on('click', function() {
          if (!$(this).parent().hasClass('item-active')) {
            $('.header-gnbitem').removeClass('item-active');
          }
          $(this).parents(".header-gnbitem").toggleClass("item-active");
        });
      });
    });
  });
})();
// personal-CnLTtXi9Ke [RPLTTXs3XB]
(function() {
  $(function() {
    $(".personal-CnLTtXi9Ke").each(function() {
      const $block = $(this);
      const $dim = $block.find('.header-dim');
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 120) {
          $block.addClass("header-top-active");
        } else {
          $block.removeClass("header-top-active");
        }
      });
      // Header Mobile 1Depth Click
      if (window.innerWidth <= 992) {
        $block.find(".header-gnbitem").each(function() {
          const $gnblink = $(this).find(".header-gnblink");
          const $sublist = $(this).find(".header-sublist");
          if ($sublist.length) {
            $gnblink.attr("href", "javascript:void(0);");
          }
        });
      }
      // Mobile Lang
      $block.find('.header-langbtn').on('click', function() {
        $(this).parent().toggleClass('lang-active');
      });
      // Mobile Top
      $block.find('.btn-momenu').on('click', function() {
        $block.addClass('momenu-active');
        $dim.fadeIn();
      });
      $block.find('.btn-close, .header-dim').on('click', function() {
        $block.removeClass('momenu-active');
        $dim.fadeOut();
      });
      // Mobile Gnb
      $block.find('.header-gnbitem').each(function() {
        const $this = $(this);
        const $thislink = $this.find('.header-gnblink');
        $thislink.on('click', function() {
          if (!$(this).parent().hasClass('item-active')) {
            $('.header-gnbitem').removeClass('item-active');
          }
          $(this).parents(".header-gnbitem").toggleClass("item-active");
        });
      });
    });
  });
})();
// personal-NxLtV5diGU [OxlTV5dZI5]
(function() {
  $(function() {
    $(".personal-NxLtV5diGU").each(function() {
      const $block = $(this);
      const $dim = $block.find('.header-dim');
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 120) {
          $block.addClass("header-top-active");
        } else {
          $block.removeClass("header-top-active");
        }
      });
      // Header Mobile 1Depth Click
      if (window.innerWidth <= 992) {
        $block.find(".header-gnbitem").each(function() {
          const $gnblink = $(this).find(".header-gnblink");
          const $sublist = $(this).find(".header-sublist");
          if ($sublist.length) {
            $gnblink.attr("href", "javascript:void(0);");
          }
        });
      }
      // Mobile Lang
      $block.find('.header-langbtn').on('click', function() {
        $(this).parent().toggleClass('lang-active');
      });
      // Mobile Top
      $block.find('.btn-momenu').on('click', function() {
        $block.addClass('momenu-active');
        $dim.fadeIn();
      });
      $block.find('.btn-close, .header-dim').on('click', function() {
        $block.removeClass('momenu-active');
        $dim.fadeOut();
      });
      // Mobile Gnb
      $block.find('.header-gnbitem').each(function() {
        const $this = $(this);
        const $thislink = $this.find('.header-gnblink');
        $thislink.on('click', function() {
          if (!$(this).parent().hasClass('item-active')) {
            $('.header-gnbitem').removeClass('item-active');
          }
          $(this).parents(".header-gnbitem").toggleClass("item-active");
        });
      });
    });
  });
})();
// personal-CyLtv4t579 [XylTV6Xh62]
(function() {
  $(function() {
    $(".personal-CyLtv4t579").each(function() {
      const $block = $(this);
      const $dim = $block.find('.header-dim');
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 120) {
          $block.addClass("header-top-active");
        } else {
          $block.removeClass("header-top-active");
        }
      });
      // Header Mobile 1Depth Click
      if (window.innerWidth <= 992) {
        $block.find(".header-gnbitem").each(function() {
          const $gnblink = $(this).find(".header-gnblink");
          const $sublist = $(this).find(".header-sublist");
          if ($sublist.length) {
            $gnblink.attr("href", "javascript:void(0);");
          }
        });
      }
      // Mobile Lang
      $block.find('.header-langbtn').on('click', function() {
        $(this).parent().toggleClass('lang-active');
      });
      // Mobile Top
      $block.find('.btn-momenu').on('click', function() {
        $block.addClass('momenu-active');
        $dim.fadeIn();
      });
      $block.find('.btn-close, .header-dim').on('click', function() {
        $block.removeClass('momenu-active');
        $dim.fadeOut();
      });
      // Mobile Gnb
      $block.find('.header-gnbitem').each(function() {
        const $this = $(this);
        const $thislink = $this.find('.header-gnblink');
        $thislink.on('click', function() {
          if (!$(this).parent().hasClass('item-active')) {
            $('.header-gnbitem').removeClass('item-active');
          }
          $(this).parents(".header-gnbitem").toggleClass("item-active");
        });
      });
    });
  });
})();