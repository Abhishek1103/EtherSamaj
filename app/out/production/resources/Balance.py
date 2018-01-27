import requests
import sys
from bs4 import BeautifulSoup


def coinValueExtracter(url):
    try:
        #proxy = {"http": "http://edcguest:edcguest@172.31.52.54:3128/",
             #"https": "https://edcguest:edcguest@172.31.52.54:3128/"}
        #sourceCode = requests.get(url, proxies=proxy)
        sourceCode = requests.get(url)
        plainText = sourceCode.text
        soup = BeautifulSoup(plainText, "html.parser")

        print(soup.findAll("td")[1].text)

    except:
        print("Error!")

url = "https://rinkeby.etherscan.io/address/"+(sys.argv)[1]+"#"
coinValueExtracter(url)