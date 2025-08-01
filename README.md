# 🔐 CaptchaV3 Solver API

API en **Java Spring Boot** pour résoudre les captchas **reCAPTCHA V3**.

---

## 🚀 Usage

Faire une requête **GET** sur :  
`http://localhost:8080/SolveV3?anchor=<URL_encodée>`

### Exemple d’URL encodée  
`https%3A%2F%2Fwww.recaptcha.net%2Frecaptcha%2Fapi2%2Fanchor%3Far%3D1%26k%3D6Lf5DqUUAAAAAIHKVINTlK4DGAisCEIXM75KeUqT%26co%3DaHR0cHM6Ly93d3cuYnVyZ2Vya2luZy5mcjo0NDM.%26hl%3Dfr%26v%3DDBIsSQ0s2djD_akThoRUDeHa%26size%3Dinvisible%26anchor-ms%3D20000%26execute-ms%3D15000%26cb%3Dk8mss8jssuqm`

---

## 🎯 Réponse

- 🔑 Jeton captcha si résolu  
- ❌ `ERROR CAPTCHA` sinon

---

## 🛠️ Lancement

- Java 17+  
- Port **8080** libre (non utilisé)

Lancer avec la commande :  
```bash
java -jar Solver.jar
