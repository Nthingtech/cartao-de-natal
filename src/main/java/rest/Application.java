package rest;

import io.quarkiverse.renarde.Controller;
import io.quarkiverse.renarde.pdf.Pdf;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import model.CardData;

public class Application extends Controller {
    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance christmasCard(CardData card);
    }

    @GET
    @Path("/christmasPngCard")
    @Produces(Pdf.IMAGE_PNG)
    public TemplateInstance christmasCard() {

        CardData card = new CardData(
                "Meu amigo secreto é meu Pai!",
                """
                        O presente vai chegar com um pouquinho de atraso, mas vai chegar.
                        E retribuindo meu presente do amigo secreto do ano passado
                        vou deixar a prévia do presente aqui uma prévia do presente...hahahaha
                        """,
                "Te Amo Pai! Fernando",
                "2025"
        );

        return Templates.christmasCard(card);
    }

    @GET
    @Path("/christmasHtmlCard")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance christmasCardHTML() {

        CardData card = new CardData(
                "Meu amigo secreto é meu Pai!",
                """
                        O presente vai chegar com um pouquinho de atraso, mas vai chegar.
                        E retribuindo meu presente do amigo secreto do ano passado
                        vou deixar a prévia do presente aqui uma prévia do presente...hahahaha
                        """,
                "Te Amo Pai! Fernando",
                "2025"
        );

        return Templates.christmasCard(card);
    }

    @GET
    @Path("/christmasPdfCard")
    @Produces(Pdf.APPLICATION_PDF)
    public TemplateInstance christmasPdfCard(@QueryParam("recipient") String recipient,
                                             @QueryParam("message") String message,
                                             @QueryParam("sender") String sender,
                                             @QueryParam("year") String year) {
        // Cria o objeto com os dados vindos da URL (do formulário/botão)
        // Se algum vier nulo, usamos um valor padrão para não quebrar
        CardData card = new CardData(
                recipient != null ? recipient : "Alguém Especial",
                message != null ? message : "Feliz Natal!",
                sender != null ? sender : "Eu",
                year != null ? year : "2025"
        );
        return Templates.christmasCard(card);
    }
}
