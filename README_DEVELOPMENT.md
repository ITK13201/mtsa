# For Developer

## Usage

### 1. Move to source dir

```shell
cd maven-root/mtsa
```

### 2. Docker image build

```shell
docker compose build
```

### 3. Create Docker container

```shell
docker compose up --no-start
```

### 4. Copy built files from container to host

```shell
docker compose cp mtsa:/usr/local/src/mtsa/target ./target
```

### 5. Run MTSA

```shell
java -jar [-Xmx${MEMORY_SIZE}] ./target/mtsa-1.0-SNAPSHOT.jar
```

e.g.)

```shell
java -jar -Xmx20G ./target/mtsa-1.0-SNAPSHOT.jar
```
