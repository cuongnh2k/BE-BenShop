package com.example.bebenshop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class SentEmailUtil {

    private final JavaMailSender mJavaMailSender;

    public void verificationCode(String email, String code) throws MessagingException {
        MimeMessage message = mJavaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        message.setContent("<!DOCTYPE HTML\n" +
                "  PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "  <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG/>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "  </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <!--<![endif]-->\n" +
                "  <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\"\n" +
                "    integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "  <title></title>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width: 520px) {\n" +
                "      .u-row {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        vertical-align: top;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-100 {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 520px) {\n" +
                "      .u-row-container {\n" +
                "        max-width: 100% !important;\n" +
                "        padding-left: 0px !important;\n" +
                "        padding-right: 0px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        min-width: 320px !important;\n" +
                "        max-width: 100% !important;\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row {\n" +
                "        width: calc(100% - 40px) !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col>div {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    tr,\n" +
                "    td {\n" +
                "      vertical-align: top;\n" +
                "      border-collapse: collapse;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      margin: 0;\n" +
                "    }\n" +
                "\n" +
                "    .ie-container table,\n" +
                "    .mso-container table {\n" +
                "      table-layout: fixed;\n" +
                "    }\n" +
                "\n" +
                "    * {\n" +
                "      line-height: inherit;\n" +
                "    }\n" +
                "\n" +
                "    a[x-apple-data-detectors='true'] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: none !important;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body u_body\"\n" +
                "  style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #e7e7e7;color: #000000\">\n" +
                "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table\n" +
                "    style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #e7e7e7;width:100%\"\n" +
                "    cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "      <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #e7e7e7;\"><![endif]-->\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #ffffff;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "                                    <i style=\"font-size: 35px; font-weight: bold;\">Ben</i><i\n" +
                "                                      style=\"font-size: 35px; font-weight: bold; color: gold;\">Shop</i>\n" +
                "\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #f4fcd1;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f4fcd1;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">Dear Sir</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">This is a new password for your account:\n" +
                                                    code +
                "                                  </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">Thank you for your interest and use of\n" +
                "                                  our services!</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">Sincerely!</p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "  <!--[if mso]></div><![endif]-->\n" +
                "  <!--[if IE]></div><![endif]-->\n" +
                "</body>\n" +
                "\n" +
                "</html>", "text/html; charset=UTF-8");
        helper.setTo(email);
        helper.setSubject("Test send HTML email");
        this.mJavaMailSender.send(message);
    }
}
