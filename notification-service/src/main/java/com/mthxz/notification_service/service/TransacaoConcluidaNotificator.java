package com.mthxz.notification_service.service;

import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import com.mthxz.notification_service.factory.NotificationFactory;
import com.mthxz.notification_service.repository.ClienteRepository;
import com.mthxz.notification_service.repository.ContaRepository;
import com.mthxz.notification_service.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.mthxz.bankcommons.enums.TipoNotificacao;
import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.notification_service.factory.Notificator;

@Service
public class TransacaoConcluidaNotificator {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final ClienteRepository clienteRepository;
    private final NotificationFactory notificationFactory;

    private final Logger logger = LoggerFactory.getLogger(TransacaoConcluidaNotificator.class);

    public TransacaoConcluidaNotificator(ContaRepository contaRepository, TransacaoRepository transacaoRepository, ClienteRepository clienteRepository, NotificationFactory notificationFactory) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.clienteRepository = clienteRepository;
        this.notificationFactory = notificationFactory;
    }

    public void enviar(TransacaoConcluidaModel transacaoConcluidaModel) {
        var contaOrigem = contaRepository.findById(transacaoConcluidaModel.getOrigem()).orElseThrow();
        var clienteOrigem = clienteRepository.findById(contaOrigem.getClienteId()).orElseThrow();

        var contaDestino = contaRepository.findById(transacaoConcluidaModel.getDestino()).orElseThrow();
        var clienteDestino = clienteRepository.findById(contaDestino.getClienteId()).orElseThrow();

        var transacao = transacaoRepository.findById(transacaoConcluidaModel.getTransacao()).orElseThrow();

        // Notifications for the origin client
        var tiposOrigem = clienteOrigem.getNotificacao();
        for (TipoNotificacao tipo : tiposOrigem) {
            Notificator notificator = notificationFactory.getNotificator(tipo);
            String mensagem;
            if (tipo == TipoNotificacao.SMS) {
                switch (transacao.getStatus()) {
                    case CONFIRMADA -> mensagem = String.format("Transacao de R$%s para %s via %s concluída!",
                            transacao.getValor(), clienteDestino.getNome(), transacao.getTipo());
                    case CANCELADA -> mensagem = String.format("Transacao para %s cancelada! %s",
                            clienteDestino.getNome(), transacaoConcluidaModel.getDetalhes());
                    case FALHOU -> mensagem = String.format("Transacao %s para %s falhou! %s",
                            transacao.getTipo(), clienteDestino.getNome(), transacaoConcluidaModel.getDetalhes());
                    default -> {continue;}
                }
            } else {
                mensagem = "<html> <h1> Transacao " + transacao.getTipo()
                        + " para " + clienteDestino.getNome() + " </h1>"
                        + "<p> Valor: R$" + transacao.getValor() + " </p>"
                        + "<p> Status: " + transacao.getStatus() + " </p>"
                        + "<p> " + transacaoConcluidaModel.getDetalhes() + " </p> </html>";
            }
            notificator.enviar(clienteOrigem, mensagem);
        }

        // Notifications for the destination client
        var tiposDestino = clienteDestino.getNotificacao();
        for (TipoNotificacao tipo : tiposDestino) {
            Notificator notificator = notificationFactory.getNotificator(tipo);
            String mensagem;
            if (transacao.getStatus() != StatusTransacao.CONFIRMADA) {
                continue;
            }
            if (tipo == TipoNotificacao.SMS) {
                mensagem = String.format("Transacao de R$%s recebida de %s via %s!",
                        transacao.getValor(), clienteOrigem.getNome(), transacao.getTipo());
            } else {
                mensagem = "<html> <h1> Transacao " + transacao.getTipo()
                        + " de " + clienteOrigem.getNome() + "</h1>"
                        + "<p> Valor: R$" + transacao.getValor() + "</p>"
                        + "<p> Status: " + transacao.getStatus() + "</p>"
                        + "<p> " + transacaoConcluidaModel.getDetalhes() + " </p> </html>";
            }
            notificator.enviar(clienteDestino, mensagem);
        }

    }
}

