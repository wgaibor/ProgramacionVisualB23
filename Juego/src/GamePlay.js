var AMOUNT_DIAMONDS = 30;
var AMOUNT_BOOBLES = 30;
GamePlayManager = {
    init: function(){
        game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
        game.scale.pageAlignHorizontally = true;
        game.scale.pageAlignVertically = true;

        this.flagFirstMouseDown = false;
        this.amountDiamondsCaught = 0;
        this.endGame = false;

        this.countSmile = -1;
    },
    preload: function() {
        game.load.image('fondo', 'assets/images/background.png');
        game.load.spritesheet('horse', 'assets/images/horse.png', 84, 156, 2);
        game.load.spritesheet('diamonds', 'assets/images/diamonds.png', 81, 84, 4);
        game.load.image('explosion', 'assets/images/explosion.png');
        game.load.image('shark', 'assets/images/shark.png');
        game.load.image('fishes', 'assets/images/fishes.png');
        game.load.image('mollusk', 'assets/images/mollusk.png');

        game.load.image('booble1', 'assets/images/booble1.png');
        game.load.image('booble2', 'assets/images/booble2.png');
    },
    create: function() {
        game.add.sprite(0, 0, 'fondo');
        this.boobleArray = [];
        for(var i=0; i<AMOUNT_BOOBLES; i++){
            var xBooble = game.rnd.integerInRange(1, 1140);
            var yBooble = game.rnd.integerInRange(600, 950);
            
            var booble = game.add.sprite(xBooble, yBooble, 'booble' + game.rnd.integerInRange(1,2));
            booble.vel = 0.2 + game.rnd.frac() * 2;
            booble.alpha = 0.9;
            booble.scale.setTo( 0.2 + game.rnd.frac() );
            this.boobleArray[i] = booble;
        }

        this.mollusk = game.add.sprite(500, 150, 'mollusk');
        this.shark = game.add.sprite(500, 20, 'shark');
        this.fishes = game.add.sprite(100, 550, 'fishes');
        

        this.caballo = game.add.sprite(0, 0, 'horse');
        this.caballo.frame = 0;
        this.caballo.x = game.width / 2;
        this.caballo.y = game.height / 2;
        this.caballo.anchor.setTo(0.5);
        
        game.input.onDown.add(this.onTap, this);

        this.diamonds = [];
        for(var i = 0; i < AMOUNT_DIAMONDS; i++){
            var diamond = game.add.sprite(100, 100, 'diamonds');
            diamond.frame = game.rnd.integerInRange(0, 3);
            diamond.scale.setTo(0.30 + game.rnd.frac());
            diamond.anchor.setTo(0.5);

            diamond.x = game.rnd.integerInRange(50, 1050);
            diamond.y = game.rnd.integerInRange(50, 600);

            this.diamonds[i] = diamond;
            var rectCurrentDiamond = this.getBoundsDiamonds(diamond);
            var rectHorse = this.getBoundsDiamonds(this.caballo);

            while(this.isOverlapingOtherDiamond(i, rectCurrentDiamond) ||
                this.isRectanglesOverlapping(rectHorse, rectCurrentDiamond)){
                diamond.x = game.rnd.integerInRange(50, 1050);
                diamond.y = game.rnd.integerInRange(50, 600);
                rectCurrentDiamond = this.getBoundsDiamonds(diamond);
            }
        }

        this.explosionGroup = game.add.group();
        
        for(var i = 0; i < 10; i++){
            this.explosion = this.explosionGroup.create(100, 100, 'explosion');
            this.explosion.tweenScale = game.add.tween(this.explosion.scale).to({
                        x: [0.4, 0.8, 0.4],
                        y: [0.4, 0.8, 0.4]
            }, 600, Phaser.Easing.Exponential.Out, false, 0, 0, false);
    
            this.explosion.tweenAlpha = game.add.tween(this.explosion).to({
                        alpha: [1, 0.6, 0]
            }, 600, Phaser.Easing.Exponential.Out, false, 0, 0, false);
    
            this.explosion.anchor.setTo(0.5);
            this.explosion.kill();
            this.explosion.visible = false; 
        }
           
        this.currentScore = 0;
        var style = {
            font: 'bold 30pt Arial',
            fill: '#ffffff',
            align: 'center'
        }
        this.scoreText = game.add.text(game.width / 2, 40, '0', style);
        this.scoreText.anchor.setTo(0.5);

        var style = {
            font: 'italic 30pt Arial',
            fill: '#FF0000',
            align: 'center'
        }
        this.totalTime = 40;
        this.timerText = game.add.text(1000, 40, this.totalTime+'', style);
        this.timerText.anchor.setTo(0.5);

        this.timerGameOver = game.time.events.loop(Phaser.Timer.SECOND, function(){
            if(this.flagFirstMouseDown) {
                this.totalTime--;
                this.timerText.text = this.totalTime+'';
                if(this.totalTime <= 0){
                    game.time.events.remove(this.timerGameOver);
                    this.showFinalMessage('GAME OVER');
                    this.endGame = true;
                }
            }
        },this);

        
    },
    increaseScore:function(){
        this.countSmile = 0;
        this.caballo.frame = 1;


        this.currentScore += 100;
        this.scoreText.text = this.currentScore;

        this.amountDiamondsCaught += 1;
        if(this.amountDiamondsCaught >= AMOUNT_DIAMONDS) {
            game.time.events.remove(this.timerGameOver);
            this.showFinalMessage('CONGRATULATION');
            this.endGame = true;
        }
    },
    showFinalMessage:function(msg) {
        this.tweenMollusk.stop();
        var bgAlpha = game.add.bitmapData(game.width, game.height);
        bgAlpha.ctx.fillStyle = '#000000';
        bgAlpha.ctx.fillRect(0,0,game.width, game.height);
        
        var bg = game.add.sprite(0,0,bgAlpha);
        bg.alpha = 0.5;
        
        var style = {
            font: 'bold 60pt Arial',
            fill: '#FFFFFF',
            align: 'center'
          }
        
        this.textFieldFinalMsg = game.add.text(game.width/2, game.height/2, msg, style);
        this.textFieldFinalMsg.anchor.setTo(0.5);
    },
    getBoundsDiamonds:function(currentDiamonds){
        return new Phaser.Rectangle(currentDiamonds.left, currentDiamonds.top, 
            currentDiamonds.width, currentDiamonds.height);
    },
    isRectanglesOverlapping: function(rect1, rect2) {
        if(rect1.x> rect2.x+rect2.width || rect2.x> rect1.x+rect1.width){
            return false;
        }
        if(rect1.y> rect2.y+rect2.height || rect2.y> rect1.y+rect1.height){
            return false;
        }
        return true;
    },
    isOverlapingOtherDiamond:function(index, rect2){
        for(var i=0; i<index; i++){
            var rect1 = this.getBoundsDiamonds(this.diamonds[i]);
            if(this.isRectanglesOverlapping(rect1, rect2)){
                return true;
            }
        }
        return false;
    },
    onTap:function(){
        if(!this.flagFirstMouseDown){
            this.tweenMollusk = game.add.tween(this.mollusk.position).to( {y: -0.001}, 5800, Phaser.Easing.Cubic.InOut, true, 0, 1000, true).loop(true);
        }
        this.flagFirstMouseDown = true;
    },
    getBoundsHorse: function(){
        var x0 = this.caballo.x  - Math.abs(this.caballo.width) / 4;
        var width = Math.abs(this.caballo.width) / 2;
        var y0 = this.caballo.y - this.caballo.height / 2;
        var height = this.caballo.height;

        return new Phaser.Rectangle(x0, y0, width,height);
    },
    render: function(){
        //game.debug.spriteBounds(this.caballo);
        for(var i = 0; i < AMOUNT_DIAMONDS; i++){
            //game.debug.spriteBounds(this.diamonds[i]);
        }
    },
    update: function() {
        if(this.flagFirstMouseDown && !this.endGame) {
            for(var i= 0; i < AMOUNT_BOOBLES; i++){
                var booble = this.boobleArray[i];
                booble.y -= booble.vel;
                if(booble.y < -50){
                    booble.y = 700;
                    booble.x = game.rnd.integerInRange(1, 1140);
                }
            }


            if(this.countSmile >= 0){
                this.countSmile++;
                if(this.countSmile > 50){
                    this.countSmile = -1;
                    this.caballo.frame = 0;
                }
            }

            this.shark.x--;
            if(this.shark.x <- 300){
                this.shark.x = 1300;
            }

            this.fishes.x+=5.3;
            if(this.fishes.x > 1300){
                this.fishes.x = -300;
            }

            var pointerX = game.input.x;
            var pointerY = game.input.y;
    
            var distX = pointerX - this.caballo.x;
            var distY = pointerY - this.caballo.y;
    
            if(distX > 0){
                this.caballo.scale.setTo(1,1);
            } else {
                this.caballo.scale.setTo(-1, 1);
            }
    
            this.caballo.x += distX * 0.02;
            this.caballo.y += distY * 0.02;

            for(var i = 0; i < AMOUNT_DIAMONDS; i++){
                var rectHorse = this.getBoundsHorse();
                var rectDiamond = this.getBoundsDiamonds(this.diamonds[i]);
                if(this.diamonds[i].visible &&
                    this.isRectanglesOverlapping(rectHorse, rectDiamond)){

                        this.increaseScore();
                        this.diamonds[i].visible = false;

                        var explosion = this.explosionGroup.getFirstDead();
                        if(explosion != null) {
                            explosion.reset(this.diamonds[i].x, this.diamonds[i].y)
                            explosion.tweenScale.start();
                            explosion.tweenAlpha.start();


                            explosion.tweenAlpha.onComplete.add(function (currentTarget, currentTween) {
                                currentTarget.kill();
                            }, this);
                        }

                }
            }
        }
        

    }
}

var game = new Phaser.Game(1136, 640, Phaser.CANVAS);

game.state.add("gameplay", GamePlayManager);
game.state.start("gameplay");