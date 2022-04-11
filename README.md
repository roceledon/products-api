# API documentation
This application is a products API service build with spring boot (v2.6.6) using webflux and a database mongo 4.4.0 as persistence. These technologies were chosen because this api does not need relational tables and the web flux speeds up the requests to list objects, by not having locks and concatenating requests.

## Swagger documentation (swagger.yaml)
### Endpoints

- list all products
```yaml
/products:
  get:
    tags:
      - product-controller
    operationId: getAllProducts
    responses:
      '200':
        description: OK
        content:
          application/json:
            schema:
              type: array
              items:
                $ref: '#/components/schemas/Product'
```

- get a product by sku
```yaml
/products/{sku}:
    get:
      tags:
        - product-controller
      operationId: getProductBySku
      parameters:
        - name: sku
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Product'
```

- store a product
```yaml
/products:
  post:
    tags:
      - product-controller
    operationId: insertProduct
    requestBody:
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/Product'
      required: true
    responses:
      '201':
        description: Created
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Product'
```

- update a product
```yaml
/products:
  put:
    tags:
      - product-controller
    operationId: updateProduct
    requestBody:
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/Product'
      required: true
    responses:
      '200':
        description: OK
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Product'
```

- delete a product by sku
```yaml
/products/{sku}:
  delete:
        tags:
          - product-controller
        operationId: deleteProductBySku
        parameters:
          - name: sku
            in: path
            required: true
            schema:
              type: string
        responses:
          '200':
            description: OK
          '400':
            description: Bad request
            content:
              application/json:
                schema:
                  $ref: '#/components/schemas/ResponseError'
```

### Objects
Product object are used to describe products in the api.
```yaml
 Product:
      type: object
      properties:
        sku:
          type: string
        name:
          type: string
        brand:
          type: string
        size:
          type: string
        price:
          type: number
          format: double
        principalImage:
          type: string
          format: url
        otherImages:
          type: array
          items:
            type: string
            format: url
```

ResponseError object are used to describe errors in the api.
```yaml
ResponseError:
  type: object
  properties:
    timestamp:
      type: string
      format: date
      example: 'yyyy-MM-ddTHH:mm:ss.SSSZ'
    status:
      type: integer
    error:
      type: string
    message:
      type: string
```

# Architecture
This api use docker and docker-compose to run. The necessary configuration information can be seen in the services section of the associated `docker-compose.yml` file:

mongodb:
```yaml
 mongodb:
   image: mongo:4.4
   restart: always
   ports:
     - 27017:27017
   volumes:
     - mongodb-data-fb:/data/db
     - mongodb-config-fb:/data/configdb
   environment:
     - MONGO_INITDB_ROOT_USERNAME=${MONGO_USERNAME}
     - MONGO_INITDB_ROOT_PASSWORD=${MONGO_PASSWORD}
   networks:
     - fb_network
```
api product: 
```yaml
  product:
    build:
      context: ./
    restart: always
    depends_on:
      - mongodb-fb
    ports:
      - 8080:8080
      - 8000:8000
    environment:
      - MONGO_HOST=${MONGO_HOST}
      - MONGO_PORT=${MONGO_PORT}
      - MONGO_USERNAME=${MONGO_USERNAME}
      - MONGO_PASSWORD=${MONGO_PASSWORD}
      - MONGO_DB=${MONGO_DB}
      - MONGO_DB_AUTH=${MONGO_DB_AUTH}
    networks:
      - fb_network
  networks:
    fb_network:
      driver: bridge
```

Network and volumes:
```yaml
networks:
  fb_network:
    driver: bridge

volumes:
  mongodb-data-fb:
  mongodb-config-fb:
```

.env file: environment variables for docker-compose. It is recommended to use secrets.
```yaml
MONGO_HOST=mongodb
MONGO_PORT=27017
MONGO_USERNAME=mongoadmin
MONGO_PASSWORD=your-pass..123
MONGO_DB=fb
MONGO_DB_AUTH=admin
```

Both containers reside on the same network - the product api is listening on port `8080` and MongoDB is
listening on the default port `27017`. The two ports are available from
outside the network so that cUrl or Postman can access them without having to be run from inside the network.

# Prerequisites
## Docker

To keep things simple both components will be run using [Docker](https://www.docker.com). **Docker** is a container
technology which allows to package each component with its environment and run it in isolation.

-   To install Docker on Windows follow the instructions [here](https://docs.docker.com/docker-for-windows/)
-   To install Docker on Mac follow the instructions [here](https://docs.docker.com/docker-for-mac/)
-   To install Docker on Linux follow the instructions [here](https://docs.docker.com/install/)

**Docker Compose** is a tool for defining and running multi-container Docker applications. Docker Compose is installed by default as part of Docker for Windows and Docker for Mac, however Linux users
will need to follow the instructions found [here](https://docs.docker.com/compose/install/)

You can check your current **Docker** and **Docker Compose** versions using the following commands:

```console
docker-compose -v
docker version
```

# Running the application

All services can be initialised from the command-line by running the bash script provided within the repository. Please
clone the repository and create the necessary images by running the commands as shown below:

```console
$ clone https://github.com/roceledon/products-api.git
$ cd products-api
$ docker-compose up -d --build
```

