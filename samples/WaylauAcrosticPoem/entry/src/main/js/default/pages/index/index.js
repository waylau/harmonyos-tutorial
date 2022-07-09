import prompt from '@system.prompt';
import fetch from '@system.fetch';

export default {
    data: {
        headPoemInput: "梦里探花花不尽",
        keywordInput: "我爱鸿蒙",
        resultPoemInput: "",
    },
    textChangeKey(e) {
        this.keywordInput = e.text;
    },
    textChangeHead(e) {
        this.headPoemInput = e.text;
    },
    genHeadPoem() {
        console.log(this.keywordInput);

        this.keywordInput = this.keywordInput.replace(/[\s]+/g, "").replace(/\n/g, "").replace(/\r/g, "");
        if (this.keywordInput == "") {
            this.showDialog("请输入4个不同的汉字", "藏头诗生成失败");
            return;
        }
        console.log(this.keywordInput);

        // AI接口
        let url = 'https://py.myie9.com/hidepoem/' + this.keywordInput;
        let that = this;
        this.resultPoemInput = "";

        fetch.fetch({
            url: url,
            method: 'GET',
            responseType: 'text',
            success: function (ret) {
                console.log(JSON.stringify(ret));

                if (ret.code == 500) {
                    that.showDialog("您太有才我不行，换一个吧", "藏头诗生成失败");
                    return;
                }

                let data = ret.data;
                console.log(data.toString());
                that.showDialog(data.toString(), "藏头诗生成成功");
            },
            fail: function (data, code) {
                if (data.code == 500) {
                    that.showDialog("您太有才我不行，换一个吧", "藏头诗生成失败");
                } else {
                    that.showDialog("发生错误，请重试。错误码：" + code + "。" + JSON.stringify(data), "AI错误");
                }
            }
        })
    },
    genPoem() {
        console.log(this.headPoemInput);

        this.headPoemInput = this.headPoemInput.replace(/[\s]+/g, "").replace(/\n/g, "").replace(/\r/g, "");
        if (this.headPoemInput == "") {
            this.showDialog("请输入一两句诗", "整首诗生成失败");
            return;
        }
        console.log(this.headPoemInput);

        // AI接口
        let url = 'https://py.myie9.com/xuxietest/' + this.headPoemInput;
        let that = this;
        this.resultPoemInput = "";

        fetch.fetch({
            url: url,
            method: 'GET',
            responseType: 'text',
            success: function (ret) {
                console.log(JSON.stringify(ret));

                if (ret.code == 500) {
                    that.showDialog("您太有才我不行，换一个吧", "整首诗生成失败");
                    return;
                }

                let data = ret.data;
                console.log(data.toString());
                that.showDialog(data.toString(), "整首诗生成成功");
            },
            fail: function (data, code) {
                if (data.code == 500) {
                    that.showDialog("您太有才我不行，换一个吧", "整首诗生成失败");
                } else {
                    that.showDialog("发生错误，请重试。错误码：" + code + "。" + JSON.stringify(data), "AI错误");
                }
            }
        })
    },
    showDialog(msg, title = '提示') {
        prompt.showDialog({
            title: title,
            message: msg,
            buttons: [{
                          text: '关闭',
                          color: '#33dd44'
                      }],
            success: function (data) {
                console.log(JSON.stringify(data));
                console.log("用户点击关闭按钮");
            },
            cancel: function () {
                console.log("用户点击按钮");
            }
        })
    }
}
