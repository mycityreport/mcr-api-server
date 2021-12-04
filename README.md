# My City Report for Citizens API Server
## このソフトウェアについて
これは [My City Report](https://www.mycityreport.jp/) として提供しているサービス "My City Report for Citizens" のサーバー部分実装です。

## 開発環境
- Java 17 or later
- Kotlin 1.6.0 or later
- Gradle 7.3 or later
- Docker & Docker Compose v2

## 開発環境の起動
Docker compose にて起動することができます。内部には実際の API サーバーと、ホットリロード用のコンテナが建っています。
従って、ソースコードを変更すると自動的にその変更部分が開発サーバーに適用されるようになっています。

```bash
$ docker compose build
$ docker compose up -d
```

## License
ソースコードについては MIT ライセンスを適用しています。
