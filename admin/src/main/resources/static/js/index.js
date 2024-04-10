var instance; // 将 instance 提升到全局作用域

document.addEventListener('DOMContentLoaded', function() {
    instance = jsPlumb.getInstance({
        container: document.querySelector('.server'),
        Endpoint: ["Blank", {}],
        Connector: ["Straight"]
    });
});
//重播
function replayAnimation() {
    var identity = document.querySelector('.identity');
    var data = document.querySelector('.data');
    // var approval = document.querySelector('.approval');

    // 重置动画和元素状态
    identity.style.animation = 'none';
    data.style.animation = 'none';
    // approval.style.display = 'none';

    identity.offsetHeight; // 触发重绘
    data.offsetHeight;

    // 开始身份信息动画
    identity.style.animation = 'moveIdentity 2s linear forwards';

    // 身份信息动画完成后显示审核通过图像
    identity.addEventListener('animationend', function() {
        // approval.style.display = 'block';

        // 在展示审核通过图像一段时间后开始数据动画
        setTimeout(function() {
            // approval.style.display = 'none';
            data.style.animation = 'moveData 2s linear forwards';
        }, 1000); // 1秒后开始数据动画
    }, { once: true });
}

// 绑定事件监听器到按钮
// document.getElementById('replay-animation').addEventListener('click', function() {
//     replayAnimation();
// });

//
// document.getElementById('toggle-emergency').addEventListener('click', function() {
//     // 如果正在进行动画，则不执行任何操作
//     if (window.isAnimating) return;
//
//     // 设置正在进行动画的标志
//     window.isAnimating = true;
//
//     var node1 = document.getElementById('node1');
//     var node2 = document.getElementById('node2');
//     var node3 = document.getElementById('node3');
//     var emergencyServer = document.getElementById('emergency-server');
//     var server = document.querySelector('.server');
//     setTimeout(function() {
//         removeAnimationClasses([node1, node2, node3, emergencyServer]);
//     }, 500);
//     if (node1.style.display === 'none') {
//         node1.style.display = 'block';
//         node1.classList.add('animate__animated', 'animate__fadeIn');
//         node2.style.display = 'block';
//         node2.classList.add('animate__animated', 'animate__fadeIn');
//         node3.style.display = 'block';
//         node3.classList.add('animate__animated', 'animate__fadeIn');
//         emergencyServer.style.display = 'none';
//
//         setTimeout(function() {
//             connectNodes();
//             // 动画完成，重置动画标志
//             window.isAnimating = false;
//         }, 500);
//
//         server.style.width = '252px';
//         server.style.height = '252px';
//         server.style.left = '60%';
//
//
//     } else {
//         node1.classList.add('animate__animated', 'animate__fadeOut');
//         node2.classList.add('animate__animated', 'animate__fadeOut');
//         node3.classList.add('animate__animated', 'animate__fadeOut');
//
//         node1.addEventListener('animationend', () => {
//             node1.style.display = 'none';
//             node2.style.display = 'none';
//             node3.style.display = 'none';
//             // 动画完成，重置动画标志
//             window.isAnimating = false;
//         }, { once: true });
//
//         emergencyServer.style.display = 'block';
//         emergencyServer.classList.add('animate__animated', 'animate__fadeIn');
//
//         instance.deleteEveryConnection();
//
//         server.style.width = '100px';
//         server.style.height = '100px';
//         server.style.left = '80%';
//     }
// });

var isButtonClicked = 0;

//模拟入侵
document.getElementById('simulate-intrusion').addEventListener('click', function() {
    var node3 = document.getElementById('node3');
    var exclamationMark = document.getElementById('exclamation-mark');
    var emergencyServer = document.getElementById('emergency-server');
    isButtonClicked = 1;
    // 检查当前是否是网络视图
    if (emergencyServer.style.display === 'none') {
        // 更改 node3 的背景图片为被入侵的图片
        node3.style.backgroundImage = 'url("/images/bug.png")';
        // 显示感叹号，你可以调整其位置和样式
        setTimeout(function() {
            exclamationMark.style.display = 'block';
            exclamationMark.style.position = 'absolute';
            // 删除所有节点之间的连线
            setTimeout(function() {
                // 删除所有节点之间的连线
                fadeOutConnections(instance);
            }, 500);
        }, 1000); // 1000毫秒后显示提示，你可以根据需要调整这个时间
    }
});

// 复原入侵按钮事件
document.getElementById('reset-intrusion').addEventListener('click', function() {
    var node3 = document.getElementById('node3');
    var exclamationMark = document.getElementById('exclamation-mark');
    var emergencyServer = document.getElementById('emergency-server');
    isButtonClicked = 0;
    // 检查当前是否是网络视图
    if (emergencyServer.style.display === 'none') {
        // 恢复 node3 的原始背景图片
        node3.style.backgroundImage = 'url("/images/shield.png")';
        // 隐藏提示信息
        exclamationMark.style.display = 'none';

        // 重新添加连线
        setTimeout(function() {
            connectNodes();
        }, 1000);
    }
});
function connectNodes() {
    // 连线的通用设置
    var common = {
        anchors: ["Right", "Left"], // 定义锚点位置
        paintStyle: {
            stroke: "#242425", // 基础颜色
            strokeWidth: 2,
            gradient: {
                // 定义渐变，从蓝色到白色
                stops: [
                    [0, "#99999b"], // 起点颜色
                    [1, "#313030"] // 终点颜色
                ]
            }
        }, // 定义绘制样式
        endpoint: "Blank", // 端点样式
    };

    // 创建第一条连线，并应用通用设置
    instance.connect({
        source: 'node1',
        target: 'node2',
        anchors: [[0.5, 1], [0.95, 0.5]],
        paintStyle: common.paintStyle,
        endpoint: common.endpoint
    });

    // 创建第二条连线，并应用通用设置
    instance.connect({
        source: 'node2',
        target: 'node3',
        anchors: [[0.95, 0.5], [0.4, 0.1]],
        paintStyle: common.paintStyle,
        endpoint: common.endpoint
    });

    // 创建第三条连线，并应用通用设置
    instance.connect({
        source: 'node3',
        target: 'node1',
        anchors: [[0.4, 0.1], [0.5, 1]],
        paintStyle: common.paintStyle,
        endpoint: common.endpoint
    });
}

function fadeOutConnections(instance) {
    const connections = instance.getAllConnections();
    let opacity = 1.0;
    const interval = setInterval(() => {
        opacity -= 0.1;
        connections.forEach(connection => {
            let paintStyle = connection.getPaintStyle();
            paintStyle.strokeOpacity = opacity;
            connection.setPaintStyle(paintStyle);
            connection.repaint();
        });

        if (opacity <= 0) {
            clearInterval(interval);
            instance.deleteEveryConnection();
        }
    }, 100); // 这里的 100 毫秒是递减间隔，可以根据需要调整
}

document.addEventListener('DOMContentLoaded', function() {
    // 设置定时器，每5秒钟检查一次状态
    checkServerStatus();
    setInterval(checkServerStatus, 6000);
});

function checkServerStatus() {
    replayAnimation();
    const id = document.getElementById('idSelect').value;
    if (id === undefined || id === null || isNaN(id)) {
        console.error('Invalid ID:', id);
        return; // 停止执行函数
    }

    fetch(`/system/sticky/check/${id}`)
        .then(response => response.json())
        .then(data => {
            const status = data.status;
            console.log('status:', status); // 输出整个
            console.log('id:', id); // 输出整个
            if (isButtonClicked === 0) {
                updateViewBasedOnStatus(status);  // 假设我们这里要切换到网络状态视图
            } else {
                // console.log('跳过视图更新');
            }

        })
        .catch(error => console.error('Error fetching status:', error));
}
var previousStatus = 0;

// 移除动画类的方法
function removeAnimationClasses(nodes) {
    nodes.forEach(node => {
        node.classList.remove('animate__animated', 'animate__fadeIn', 'animate__fadeOut');
    });
}

function updateViewBasedOnStatus(status) {
    var node1 = document.getElementById('node1');
    var node2 = document.getElementById('node2');
    var node3 = document.getElementById('node3');
    var server = document.querySelector('.server');
    var emergencyServer = document.getElementById('emergency-server');
    if (previousStatus !== status) {
        // 当前状态与上一次状态不同，刷新页面
        // location.reload();
        previousStatus = status;
    }
    // 假设0代表紧急服务器视图，1代表网络状态视图
    setTimeout(function() {
        removeAnimationClasses([node1, node2, node3, emergencyServer]);
    }, 2000);
    if (status === 1 ){
        // 切换到网络状态视图
        node1.classList.add('animate__animated', 'animate__fadeIn');
        node2.classList.add('animate__animated', 'animate__fadeIn');
        node3.classList.add('animate__animated', 'animate__fadeIn');
        node1.style.display = 'block';
        node2.style.display = 'block';
        node3.style.display = 'block';
        emergencyServer.style.display = 'none';

        setTimeout(function() {
            connectNodes();
            // 动画完成，重置动画标志
            window.isAnimating = false;
        }, 500);

        server.style.width = '252px';
        server.style.height = '252px';
        server.style.left = '60%';
    } else {
        // 切换到紧急服务器视图
        node1.classList.add('animate__animated', 'animate__fadeOut');
        node2.classList.add('animate__animated', 'animate__fadeOut');
        node3.classList.add('animate__animated', 'animate__fadeOut');

        node1.addEventListener('animationend', () => {
            node1.style.display = 'none';
            node2.style.display = 'none';
            node3.style.display = 'none';
            // 动画完成，重置动画标志
            window.isAnimating = false;
        }, { once: true });

        emergencyServer.style.display = 'block';
        emergencyServer.classList.add('animate__animated', 'animate__fadeIn');

        instance.deleteEveryConnection();

        server.style.width = '100px';
        server.style.height = '100px';
        server.style.left = '80%';
    }
}

<!--折线图-->
    // 折线图
    var myChart = echarts.init(document.getElementById('lineChart'));
    function getFormattedDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);
    return year + "-" + month + "-" + day;
}

    const url = '/system/actionLog/dates/' + getFormattedDate();
    // 使用jQuery发起Ajax请求（假设你的后端API返回的数据格式与serverCapacities一致）
    $(document).ready(function() {
    $.ajax({
        url:url,
        method: 'GET',
        success: function(response) {
            console.log('url:', url); // 输出整个
            let serverCapacities = response;
            const dates = Object.keys(serverCapacities);
            // 将对象转换为数组，并按照键（日期）排序
            const sortedEntries = Object.entries(response).sort((a, b) => new Date(a[0]).getTime() - new Date(b[0]).getTime());

            // 获取最后两个条目的日志数量
            const [lastDate, lastCount] = sortedEntries.pop();
            const [penultimateDate, penultimateCount] = sortedEntries.pop();

            // 计算差值
            const diff = lastCount - penultimateCount;

            // 更新DOM元素中的数据
            $('#newLogsCount').text(diff);
            const series = {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: 'rgb(66,111,251)' }, // 开始颜色（例如红色）
                    { offset: 1, color: 'rgb(24,228,253)' }   // 结束颜色（例如绿色）
                ]),
                name: 'Server Capacity',
                type: 'line',
                smooth: true,
                symbolSize: 10,
                emphasis: {
                    focus: 'series',
                    symbolSize: 25,
                },
                lineStyle: {
                    width: 4
                },
                data: dates.map(date => serverCapacities[date]) // 根据日期获取对应的服务器容量
            };

            // 定义 ECharts 图表配置项
            const option = {
                tooltip: {
                    trigger: 'axis',
                    formatter: (params) => `
            <div style="font-size: larger;">${params[0].name}</div>
            日志数量: ${params[0].value * 1}
        `,
                    backgroundColor: 'rgb(236,244,251)', // 可选，设置背景颜色以提高可读性
                    padding: [10, 15], // 可选，设置内边距
                    textStyle: { // 可选，设置文字样式
                        color: '#000000',
                        fontSize: 14 // 这里统一设置了tooltip的文字大小，如果你想让日期更大，可以在这里或formatter中第一行单独指定更大的字号
                    },
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    axisLabel: {
                        margin: 30,
                        fontSize: 16
                    },
                    data: dates
                },
                yAxis: {
                    type: 'value',
                    show: true,
                    axisLabel: {
                        margin: 30,
                        fontSize: 16,
                        formatter: '{value} ' // 假设单位是GB
                    },
                    splitNumber: 5, // 这个属性可以根据需求调整纵坐标轴的分割段数，默认为5
                    interval: Math.max.apply(null, series.data) / 5, // 自动计算一个合适的间隔值，这里假设最大值分为5份
                    min: 0 // 根据实际情况设定最小值
                },
                series: [series]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },
        error: function(err) {
        }
    });
});
<!--地图-->
    var myMap = echarts.init(document.getElementById('map'));
    var geoCoordMap = {
    // 国内城市
    '北京': [116.46, 39.92],
    '成都': [104.06, 30.67],
    '广州': [113.23, 23.16],
    '海口': [110.35, 20.02],
    '哈尔滨': [126.63, 45.75],
    '杭州': [120.19, 30.26],
    '呼和浩特': [111.65, 40.82],
    '三亚': [109.511909, 18.252847],
    '上海': [121.48, 31.22],
    '深圳': [114.07, 22.62],
    '珠海': [113.52, 22.3],
    '乌鲁木齐': [87.68, 43.77],
    // 国际城市
    '伦敦': [-0.1277583, 51.5073509],
    '纽约': [-74.0060152, 40.7127281],
    '巴黎': [2.3522219, 48.856614],
    '悉尼': [151.209295, -33.868820],
    '东京': [139.767137, 35.689487],
};
    $.ajax({
    url: '/system/locking/locate',
    method: 'GET',
    success: function(response) {
    // console.log('原始响应:', response);
    var data = [];
    for (let [location, count] of Object.entries(response)) {
    if (geoCoordMap[location]) {  // 确保location在geoCoordMap中有对应的坐标
    data.push({ name: location, value: count });
}
}
    // console.log('转换后的data:', data);
    var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
    var geoCoord = geoCoordMap[data[i].name];
    if (geoCoord) {
    res.push({
    name: data[i].name,
    value: geoCoord.concat(data[i].value)
});
}
}
    return res;
};

    option = {
    // backgroundColor: '#f1f1f1',
    tooltip: {
    trigger: 'item',
    formatter: function(params) {
    return '<div style="background-color:#fff;padding:5px 10px;">'
    + '<p style="color:#333;font-size:16px;font-weight:bold;margin:0;">' + params.name + '</p>'
    + '<p style="font-size:14px;margin:0;">服务节点数量：' + params.value[2] + '</p>'
    + '</div>';
},
},
    geo: {
    map: 'world',
    zoom: 1.2,//放大p-==-
    label: {
    emphasis: {
    show: false
}
},
    roam: true,
    itemStyle: {
    normal: {
    areaColor: 'rgba(213,213,213,0.5)',
    borderColor: 'rgba(112,187,252,.5)'
},
    emphasis: {
    areaColor: 'rgba(2,37,101,.8)'
}
}
},
    series: [
{
    name: '服务节点数量',
    type: 'effectScatter',
    coordinateSystem: 'geo',
    rippleEffect: {
    brushType: 'stroke'
},
    data: convertData(data),
    symbolSize: function (val) {
    return val[2]*8 ;
},
    label: {
    normal: {
    formatter: '{b}',
    position: 'right',
    show: false
},
    emphasis: {
    show: false
}
},
    itemStyle: {
    normal: {
    color: '#7bc4ff'
}
}
}

    ]
};
    myMap.setOption(option);
    window.addEventListener("resize", function () {
    myMap.resize();
});
},
    error: function(jqXHR, textStatus, errorThrown) {
    console.error('Error occurred while fetching location counts:', textStatus, ', Details:', errorThrown);
}
});
    // var data = [
    //     { name: '上海', value: 8 },
    //     { name: '海口', value: 8 },
    //     { name: '哈尔滨', value: 8 },
    //     { name: '北京', value: 8 },
    // ];
    layui.use(['carousel'], function(){
    var carousel = layui.carousel;
    // 轮播图配置
    carousel.render({
    elem: '#carouselDemo' // 匹配容器
    ,width: '100%' // 宽度
    ,height: '300px' // 高度
    ,arrow: 'always' // 是否始终显示箭头
    ,interval: 3000 // 切换间隔时间
});
});
    $(document).ready(function() {
    // 为所有具有'popup-image'类的图片绑定点击事件
    $('.popup-image').click(function(e) {
        e.preventDefault(); // 阻止默认的链接行为

        // 触发UI库的弹窗功能
        // 假设layui会自动处理data-url和data-title这样的属性
        $(this).trigger('open-popup');
    });
});