# POC AWS AppConfig Agent

Essa POC visa validar uma integração com o Agent do AWS AppConfig.

## Configuração e ambiente

Na POC, é utilizada a imagem do agent do AWS AppConfig apontando para arquivos locais em modo de desenvolvimento.
Os arquivos devem seguir a seguinte estrutura:

`NomeAplicacao:Ambiente:ChaveConfiguracao`

Os arquivos de exemplo na POC foram criados na pasta `config`. O conteúdo desses arquivos de configuração pode ser um valor simples, como exemplificado pelo arquivo `PocAppConfig:Development:SimpleConfig` ou de _Feature Flags_ podendo conter diversas configurações, como exemplificado no arquivo `PocAppConfig:Development:FeatureFlagsConfig`.

Nesse segundo caso, o conteúdo deve ser um json válido. O atributo `enabled` é obrigatório para obtenção de chaves específicas em um mesmo arquivo.

## Execução

- Na raiz do projeto, execute o seguinte comando para construir a aplicação: `gradle clean build`
- Em seguida, ainda na raiz, o seguinte comando para subir a aplicação e o AWS AppConfig, já configurado para ambiente de desenvolvimento, leitura dos arquivos locais e _polling_ a cada 5 segundos: `docker-compose up --build`
- Em outro terminal, o comando para leitura de todas as configurações em `PocAppConfig:Development:FeatureFlagsConfig`: `curl localhost:8080/config`
- Comando para obtenção de uma chave específica: `curl localhost:8080/config/key_3`
- Comando para leitura do valor simples em `PocAppConfig:Development:SimpleConfig`: `curl localhost:8080/config/simple`

A qualquer momento, os valores nos arquivos podem ser alterados e novas flags podem ser adicionadas. Após alguns segundos, a atualização é identificada automaticamente e as chamadas podem ser realizadas mais uma vez para conferência dos valores atualizados.