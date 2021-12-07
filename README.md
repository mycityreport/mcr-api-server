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

## 開発の流れ
まず GitHub Projects にタスクを登録して、それを Issue に変換してください。それに対して Pull Request を作成します。

各開発用ブランチは `develop` から切るようにして、`feature/foo` `fix/bar` のように命名するようにしてください。この名前で作ると GitHub Actions が走るようになっています。

なお、そのまま push すると lint や test に引っかかる可能性があります。Push 前にはそれらを実行しておくと良いでしょう。 

```bash
$ git switch -c feature/foo
$ ./gradlew ktlintFormat
$ ./gradlew test
$ git add .
$ git commit -m "message"
$ git push -u origin feature/foo
```

## License
ソースコードについては MIT ライセンスを適用しています。
