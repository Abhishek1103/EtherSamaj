import requests
from bs4 import BeautifulSoup

coinValue = {"id-bitcoin" : "", "id-ethereum" : "", "id-dash" : ""}

def coinValueExtracter(url):
    try:
        #proxy = {"http": "http://edcguest:edcguest@172.31.52.54:3128/",
            # "https": "https://edcguest:edcguest@172.31.52.54:3128/"}

        usdToinr = "http://www.xe.com/currencyconverter/convert/?Amount=1&From=USD&To=INR"
        #sourceCode = requests.get(usdToinr, proxies=proxy)
        sourceCode = requests.get(usdToinr)
        plainText = sourceCode.text
        soup = BeautifulSoup(plainText, "html.parser")
        try:
            print(soup.findAll("span", {"class":"uccResultAmount"})[0].text)
        except:
            print("62.50")


        #sourceCode = requests.get(url, proxies=proxy)
        sourceCode = requests.get(url)
        plainText = sourceCode.text
        soup = BeautifulSoup(plainText, "html.parser")

        for i in coinValue.keys():
            link = soup.findAll("tr", {"id":i})
            print(link[0].findAll("a",{"class":"price"})[0].text)
            #coinValue[i] = link[0].findAll("a",{"class":"price"})[0].text


    except:
        print("Error!")

url = "https://coinmarketcap.com/"

coinValueExtracter(url)