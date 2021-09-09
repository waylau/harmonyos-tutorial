export default {
    data: {
        likeImage: '/common/images/unLike.jpg',
        isPressed: false,
        total: 20,
    },
    likeClick() {
        var temp;
        if (!this.isPressed) {
            temp = this.total + 1;
            this.likeImage = '/common/images/like.jpg';
        } else {
            temp = this.total - 1;
            this.likeImage = '/common/images/unLike.jpg';
        }
        this.total = temp;
        this.isPressed = !this.isPressed;
    }
}