
import { Course, CourseRequest, LearningAgreement, PastEvaluation, PastEvaluationItem, PreApprovalForm, PreviousCourseRequest, UniAssociatedCourse, StudentAssociatedCourse, StudentAssociatedWishlist, UniversityDetailed, UniversityProxy, User } from ".";

export type ResponseUser = Record<'data', User>

export type ResponseStudentCourseWishlist = Record<'data', Array<StudentAssociatedWishlist>>

export type ResponseAllStudentCourseWishlist = Record<'data', Array<StudentAssociatedCourse>>

export type ResponseAllSubmitedPreApprovals = Record<'data', Array<PreApprovalForm>>

export type ResponseApprovePreApproval = Record<'data', PreApprovalForm>

export type ResponseCourseList = Record<'data', Array<Course>>

export type ResponseCourse = Record<'data', Course>

export type ResponseUniSpecificCourses = Record<'data', Array<UniAssociatedCourse>>

export type ResponseStudentSpecificCourseWishlist = Record<'data', StudentAssociatedWishlist>

export type ResponsePreviousCourseRequests = Record<'data', Array<PreviousCourseRequest>>

export type ResponseCourseRequest = Record<'data', CourseRequest>

export type ResponsePreApprovalForm = Record<'data', PreApprovalForm>

export type ResponsePreApprovalFormList = Record<'data', Array<PreApprovalForm>>

export type ResponseUniversities = Record<'data', Array<UniversityProxy>>

export type ResponseUniversity = Record<'data', UniversityDetailed>

export type ResponseEvaluation = Record <'data', PastEvaluation>

export type ResponseAllLearningAgreements = Record <'data', Array<LearningAgreement>>