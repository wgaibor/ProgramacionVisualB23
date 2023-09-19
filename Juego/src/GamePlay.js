
GamePlayManager = {
    init: function(){
        game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
        game.scale.pageAlignHorizontally = true;
        game.scale.pageAlignVertically = true;
    },
    preload: function() {
        game.load.image('fondo', 'assets/images/background.png');
        game.load.spritesheet('horse', 'assets/images/horse.png', 84, 156, 2);
    },
    create: function() {
        game.add.sprite(0, 0, 'fondo');
        var caballo = game.add.sprite(0,0, 'horse');
        caballo.frame = 1;
    },
    update: function() {
        console.log("update");
    }
}

var game = new Phaser.Game(1136, 640, Phaser.CANVAS);

game.state.add("gameplay", GamePlayManager);
game.state.start("gameplay");