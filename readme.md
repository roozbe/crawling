# Sample web crawler project for finology job interview

Web crawler for collecting product details from [http://magento-test.finology.com.my/](http://magento-test.finology.com.my/)


## Installation

Use the package manager **maven** to install project.

```bash
mvn clean package
mvn spring-boot:run
```
After running the project, it will initially start to collecting all products detail. The project uses BloomFilter to prevent listing the same product more than once.
## Swagger
Swagger Url : ```http://localhost:9585/swagger-ui.html```

## Endpoints
* Persisting product detail to database ```http://localhost:9585/products```
* Fetch Product Detail by given Id ```http://localhost:9585/products/{id}```
* Fetch Product Detail by Its Url ```http://localhost:9585/urls```
* Fetch All Product Urls ```http://localhost:9585/urls```
