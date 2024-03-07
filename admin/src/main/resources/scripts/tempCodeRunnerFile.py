import sys
import requests
#
# # 获取命令行参数
# device_fingerprint = sys.argv[1]
# operating_system_info = sys.argv[2]
# cpu_info = sys.argv[3]
# memory_info = sys.argv[4]
# hard_disk_info = sys.argv[5]
# network_card_info = sys.argv[6]
# manufacturer = sys.argv[7]
# model_number = sys.argv[8]
# serial_number = sys.argv[9]
# ip_address = sys.argv[10]
#
# # 打印接收到的参数
# print(f"设备指纹：{device_fingerprint}")
# print(f"操作系统信息：{operating_system_info}")
# print(f"CPU信息：{cpu_info}")
# print(f"内存信息：{memory_info}")
# print(f"硬盘信息：{hard_disk_info}")
# print(f"网卡信息：{network_card_info}")
# print(f"生产厂商：{manufacturer}")
# print(f"主机型号：{model_number}")
# print(f"SN码：{serial_number}")
# print(f"IP地址：{ip_address}")

# 这里可以添加你的实际业务处理逻辑

data = {
    'os_type': "1",
    'os_release': "2",
    'os_distribution': 'Microsoft',
    'asset_type': 'server'
}

# 定义服务器主机和端口
host = "172.16.0.103"
port = 9000


send_data = str(data)

# 定义要发送的数据
data_to_send = send_data.encode('utf-8')

# 定义请求头

headers = {
    "Content-Type": "application/json",
    "Content-Length": str(len(data_to_send))
}


# 发送POST请求
try:
    url = f"http://{host}:{port}"
    response = requests.post(url, data=data_to_send, headers=headers)

    # 检查响应状态码
    if response.status_code == 200:
        print("[+] Request succeeded!")
        print("[+] Server response :", response.text)
    else:
        print(f"Request failed with status code {response.status_code}")
except Exception as e:
    print("Error:", e)