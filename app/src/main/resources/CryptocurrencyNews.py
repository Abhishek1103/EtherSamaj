import requests
from bs4 import BeautifulSoup


def newsExtracter(url):
    try:
        #proxy = {"http": "http://edcguest:edcguest@172.31.52.54:3128/",
          #        "https": "http://edcguest:edcguest@172.31.52.54:3128/"}
        #sourceCode = requests.get(url, proxies=proxy)
        sourceCode = requests.get(url)
        plainText = sourceCode.text
        soup = BeautifulSoup(plainText, "html.parser")

        for i in soup.findAll("a", {"class": "fade"}):
            print(i["title"],"\n",i["href"])

        #print("\n")


    except:
        print("N/A")


url1 = "https://www.coindesk.com/category/technology-news/ethereum-technology-news/"
url2 = "https://www.coindesk.com/category/technology-news/bitcoin/"

newsExtracter(url1)
newsExtracter(url2)