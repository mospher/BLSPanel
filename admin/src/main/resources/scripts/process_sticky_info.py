import sys
import time
import requests

# 获取命令行参数
create_system_value = sys.argv[1]
create_system_value = 4
data = {
    'create_system': create_system_value
}
print(" create_system_value:",create_system_value)
host = "172.16.0.103"
port = 9000
url = f"http://{host}:{port}"

headers = {
    "Content-Type": "application/json",
    "Content-Length": str(len(str(data).encode('utf-8')))
}

send_times = 1  # 设置发送次数
interval = 1  # 设置发送间隔（单位：秒）

for _ in range(send_times):
    send_data = str(data).encode('utf-8')

    try:
        response = requests.post(url, data=send_data, headers=headers)

        if response.status_code == 200:
            print(f"[+] Request succeeded! ({_ + 1}/{send_times})")
            print("[+] Server response :", response.text)
        else:
            print(f"Request failed with status code {response.status_code} ({_ + 1}/{send_times})")

        time.sleep(interval)  # 暂停一段时间再进行下一次发送

    except Exception as e:
        print(f"Error during request {_ + 1}/{send_times}: ", e)
        time.sleep(interval)  # 即使出错也暂停一段时间

print("Finished sending requests.")