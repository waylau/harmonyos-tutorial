import device from '@system.device';

export default {
    data: {
        title: 'World',
        width: 0,
        height: 0
    },
    onInit() {
        device.getInfo({
            success: (data)=>{
                this.width = data.windowWidth;
                this.height = data.windowHeight;
                console.log(`Resolution: ${this.width}*${this.height}`);
            }
        });
    }
}
